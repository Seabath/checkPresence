package com.seabath.axu614.checkPresence.activity

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import com.seabath.axu614.checkPresence.R
import java.io.ByteArrayOutputStream
import java.util.*

class TagActivity : Activity() {

    var mTxtTagContent: TextView? = null
    var mtglWrite: ToggleButton? = null
    var mNfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tag_activity)

        mTxtTagContent = findViewById(R.id.tag_content) as TextView?
        mtglWrite = findViewById(R.id.tgl_write) as ToggleButton?
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNfcAdapter == null) {
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_SHORT).show()
            finish()
            return
        } else if (!mNfcAdapter!!.isEnabled)
            mTxtTagContent?.text = "NFC is disabled."
        else
            mTxtTagContent?.setText(R.string.explanation)

        checkTag()
    }

    fun checkTag() {
        var parcelables: Array<out Parcelable>? = intent.getParcelableArrayExtra("bloblo")
        if (parcelables != null && parcelables.size > 0) {
            readTextFromMessage(parcelables[0] as NdefMessage)
        } else {
            Toast.makeText(this, "No tag found!", Toast.LENGTH_LONG).show()
        }
    }

    private fun readTextFromMessage(ndefMessage: NdefMessage) {
        var ndefRecords: Array<out NdefRecord> = ndefMessage.records

        if (ndefRecords.size > 0) {
            var ndefRecord: NdefRecord = ndefRecords[0]
            mTxtTagContent?.text = getTextFromNdefRecord(ndefRecord)
        } else
            Toast.makeText(this, "No NDEF records found found!", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        enableForegroundDispatchSystem()
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatchSystem()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.hasExtra(NfcAdapter.EXTRA_TAG) as Boolean) {
            Toast.makeText(this, "NfcIntent!", Toast.LENGTH_LONG).show()

            if (mtglWrite?.isChecked as Boolean) {
                var parcelables: Array<Parcelable> = intent?.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES) as Array<Parcelable>
                if (parcelables.size > 0) {
                    readTextFromMessage(parcelables[0] as NdefMessage)
                } else {
                    Toast.makeText(this, "No NDEF mesages found", Toast.LENGTH_LONG).show()
                }

            } else {
                var tag: Tag = intent!!.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                var ndefMessage: NdefMessage = createNdefMessage(mTxtTagContent?.text.toString())

                writeNdefMessage(tag, ndefMessage)
            }
        }
    }

    private fun enableForegroundDispatchSystem() {
        var intent: Intent = Intent(this, TagActivity::class.java).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)

        var pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        var intentFilters: Array<out IntentFilter> = Array(0, { i -> IntentFilter() })

        mNfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }


    private fun disableForegroundDispatchSystem() {
        mNfcAdapter?.disableForegroundDispatch(this)
    }

    private fun writeNdefMessage(tag: Tag, ndefMessage: NdefMessage) {
        try {

            var ndef: Ndef = Ndef.get(tag)
            ndef.connect()
            if (!ndef.isWritable) {
                Toast.makeText(this, "Tag not writable!", Toast.LENGTH_LONG).show()
                ndef.close()
                return
            }
            ndef.writeNdefMessage(ndefMessage)
            ndef.close()
            Toast.makeText(this, "Tag written!", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Log.e("writeNdefMessage", e.message)
        }
    }

    private fun createTextRecord(content: String): NdefRecord? {
        try {
            var language: ByteArray = Locale.getDefault().language.toByteArray()
            val text: ByteArray = content.toByteArray()
            val languageSize: Int = language.size
            val textSize: Int = text.size
            val payload: ByteArrayOutputStream = ByteArrayOutputStream(1 + languageSize + textSize)

            payload.write(languageSize.and(0x1F))
            payload.write(language, 0, languageSize)
            payload.write(text, 0, textSize)

            return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), payload.toByteArray())
        } catch (e: Exception) {
            Log.e("createTextRecord", e.message)
        }
        return null
    }

    private fun createNdefMessage(content: String): NdefMessage {
        var ndefRecord: NdefRecord? = createTextRecord(content)

        var ndefMessage: NdefMessage = NdefMessage(Array(1, { ndefRecord }))

        return ndefMessage
    }

    fun getTextFromNdefRecord(ndefRecord: NdefRecord): String {
        var tagContent: String = ""
        try {
            var payload: ByteArray = ndefRecord.payload
            var textEncoding: String = if (payload[0].toInt().and(128) == 0) "UTF-8" else "UTF-16"
            var languageSize: Int = payload[0].toInt().and(63)
            tagContent = String(payload, languageSize + 1,
                    payload.size - languageSize - 1)

        } catch (e: Exception) {
            Log.e("getTextFromNdefRecord", e.message, e)
        }
        return tagContent
    }
}