package maitrik.smarttimber.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.util.Log
import java.io.*

class PdfDocumentAdapter(context:Context,path:String): PrintDocumentAdapter() {
    internal var context:Context?=null
    internal var path=""
    init {
        this.context=context
        this.path=path
    }
    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
            if (cancellationSignal!!.isCanceled)
            {
                callback!!.onLayoutCancelled()
            }
        else
        {
            val builder:PrintDocumentInfo.Builder=PrintDocumentInfo.Builder("file name")
            builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                .build()
            callback!!.onLayoutFinished(builder.build(), oldAttributes != newAttributes)
        }
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        var inp:InputStream?=null
        var out:OutputStream?=null
        try {

            val file = File(path)
           // var fis= FileInputStream(file)
            inp=FileInputStream(file)
            out=FileOutputStream(destination!!.fileDescriptor)
            if (!cancellationSignal!!.isCanceled)
            {
                inp.copyTo(out)
                callback!!.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }
            else
            {
                callback!!.onWriteCancelled()
            }
        }
        catch (e:Exception)
        {
            callback!!.onWriteFailed(e.message)
            e.message?.let { Log.e("WDC", it) }
            e.printStackTrace()
        }
        finally {
        try {
            inp!!.close()
            out!!.close ()
        }catch (e:IOException)
        {
            e.printStackTrace()
        }
        }
    }

}