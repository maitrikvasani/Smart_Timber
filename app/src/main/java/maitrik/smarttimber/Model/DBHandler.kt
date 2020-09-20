package maitrik.smarttimber.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import maitrik.smarttimber.Model.Drum
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

val DATABASE_NAME = "ST"
val DATABASE_VERSION= 1
val TABLE_NAME = "TBL_Drum"
val TABLEMASTER_CUTSIZE ="TBLMASTER_CUTSIZE"
val TABLE_CUTSIZE ="TBL_CUTSIZE"
val TABLE_RLOG = "TBL_ROUNDLOG"
val COL_ID = "id"
val COL_CMM = "cmm"
val COL_CW = "cw"
val COL_CQ = "cq"
val COL_CCFT = "ccft"
val COL_CkMM = "ckmm"
val COL_CkW = "ckw"
val COL_CkQ = "ckq"
val COL_CkCFT = "ckcft"
val COL_CDIQ = "cdiq"
val COL_MMM = "mmm"
val COL_MW = "mw"
val COL_MQ = "mq"
val COL_MCFT = "mcft"
val COL_SW = "sw"
val COL_SH = "sh"
val COL_SL = "sl"
val COL_SQ = "sq"
val COL_SCFT = "scft"
val COL_TCFT = "tcft"
val COL_RATE = "rate"
val COL_AMNT = "amnt"
val COL_DATEDAY= "drumdateday"
val COL_DATEMOUNTH= "drumdatemounth"
val COL_DATEYEAR= "drumdateyear"
val COL_INCHMM="inchmm"
val COL_NAME="name"
val TABLE_PARTY="TBL_Party"
var COL_PARTY_ID="id"
var COL_PARTY_NAME="name"
var TABLE_CHALAN="TBL_chalan"
var COL_CNO="TBL_cno"
var COL_VNO="TBL_vno"
var COL_PID="pid"
var COL_date="col_date"
var COL_DATE="col_date"
var TBL_Date="tbl_date"
var COL_CSM_ID ="id"
var COL_CSM_NAME ="name"
var COL_CS_ID ="cid"
var COL_CS_W ="WIDTH"
var COL_CS_H ="HEIGHT"
var COL_CS_L ="LENGTH"
var COL_CS_Q ="QTY"
var COL_CS_CFT ="CFT"
class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    private var mContext: Context=context
    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_CMM INTEGER DEFAULT 0, $COL_CW DOUBLE DEFAULT 0, $COL_CQ INTEGER DEFAULT 0, $COL_CCFT DOUBLE DEFAULT 0," +
                "$COL_CkMM INTEGER DEFAULT 0, $COL_CkW DOUBLE DEFAULT 0, $COL_CkQ DOUBLE DEFAULT 0, $COL_CkCFT DOUBLE DEFAULT 0, $COL_CDIQ DOUBLE DEFAULT 0," +
                "$COL_MMM INTEGER DEFAULT 0, $COL_MW DOUBLE DEFAULT 0, $COL_MQ INTEGER DEFAULT 0, $COL_MCFT DOUBLE DEFAULT 0," +
                "$COL_SW INTEGER DEFAULT 0, $COL_SH DOUBLE DEFAULT 0, $COL_SL DOUBLE DEFAULT 0, $COL_SQ INTEGER DEFAULT 0, $COL_SCFT DOUBLE DEFAULT 0," +
                "$COL_TCFT DOUBLE DEFAULT 0, $COL_RATE INTEGER DEFAULT 0, $COL_AMNT INTEGER DEFAULT 0," +
                "$COL_DATEDAY INTEGER DEFAULT 0, $COL_DATEMOUNTH INTEGER DEFAULT 0, $COL_DATEYEAR INTEGER DEFAULT 0, $COL_DATE DATE, $COL_INCHMM TEXT NULL, $COL_NAME TEXT NULL," +
                "$COL_CNO INTEGER DEFAULT 0, $COL_VNO TEXT NULL)")

        db?.execSQL("CREATE TABLE $TABLEMASTER_CUTSIZE ($COL_CSM_ID INTEGER PRIMARY KEY AUTOINCREMENT , $COL_CSM_NAME TEXT NULL)")
        db?.execSQL("CREATE TABLE $TABLE_CUTSIZE ($COL_CS_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_CS_W DOUBLE DEFAULT 0, $COL_CS_H DOUBLE DEFAULT 0 ,$COL_CS_L DOUBLE DEFAULT 0," +
                " $COL_CS_Q DOUBLE DEFAULT 0 , $COL_CS_CFT DOUBLE DEFAULT 0 , $COL_CSM_ID INTEGER, FOREIGN KEY ($COL_CSM_ID) REFERENCES $TABLEMASTER_CUTSIZE ($COL_CSM_ID))")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }
    fun insertdrum(drum: Drum) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_CMM, drum.cmm)
        cv.put(COL_CW, drum.cw)
        cv.put(COL_CQ, drum.cq)
        cv.put(COL_CCFT, drum.ccft)
        cv.put(COL_CkMM, drum.ckmm)
        cv.put(COL_CkW, drum.ckw)
        cv.put(COL_CkQ, drum.ckq)
        cv.put(COL_CkCFT, drum.ckcft)
        cv.put(COL_CDIQ, drum.cdiq)
        cv.put(COL_MMM, drum.mmm)
        cv.put(COL_MW, drum.mw)
        cv.put(COL_MQ, drum.mq)
        cv.put(COL_MCFT, drum.mcft)
        cv.put(COL_SW, drum.sw)
        cv.put(COL_SH, drum.sh)
        cv.put(COL_SL, drum.sl)
        cv.put(COL_SQ, drum.sq)
        cv.put(COL_SCFT, drum.scft)
        cv.put(COL_TCFT, drum.tcft)
        cv.put(COL_RATE, drum.rate)
        cv.put(COL_AMNT, drum.amnt)
        cv.put(COL_DATEDAY,drum.dateday)
        cv.put(COL_DATEMOUNTH,drum.datemounth)
        cv.put(COL_DATEYEAR,drum.dateyear)
        cv.put(COL_DATE,drum.cdate)
        cv.put(COL_INCHMM,drum.inchmm)
        cv.put(COL_NAME,drum.name)
        cv.put(COL_CNO,drum.cno)
        cv.put(COL_VNO,drum.vno)
        var r=0
        try {
            r = db.insert(TABLE_NAME, null, cv).toInt()
            if (r==-1)
            {
                Toast.makeText(context,"Error!!!",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Success..",Toast.LENGTH_SHORT).show()
            }
        } catch (e:Exception)
        {
            Toast.makeText(context,"Error= $e",Toast.LENGTH_SHORT).show()
            Log.e(ContentValues.TAG,"Insert Error!!1")
        }
    }
    fun insertcutsizemaster(cs:CutSize)
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_CSM_NAME,cs.pname)
        var r: Int
        try {
            r = db.insert(TABLEMASTER_CUTSIZE, null, cv).toInt()
          //  r1 = db.insert(TABLE_CUTSIZE, null, cv1).toInt()
            if (r==-1)
            {
                Toast.makeText(context,"Error!!!",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Success..",Toast.LENGTH_SHORT).show()

            }

        } catch (e:Exception)
        {
            Toast.makeText(context,"Error= $e",Toast.LENGTH_SHORT).show()
            Log.e(ContentValues.TAG,"Insert Error!!1")
        }
    }

    fun insertcutsize(cs:CutSize)
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_CSM_ID,cs.mid)
        cv.put(COL_CSM_NAME,cs.pname)
        val cv1 = ContentValues()
        cv1.put(COL_CS_W,cs.width)
        cv1.put(COL_CS_H,cs.height)
        cv1.put(COL_CS_L,cs.length)
        cv1.put(COL_CS_Q,cs.qty)
        cv1.put(COL_CS_CFT,cs.cft)
        cv1.put(COL_CSM_ID,cs.mid)
        var r: Int
        try {
           // r = db.insert(TABLEMASTER_CUTSIZE, null, cv).toInt()
            r = db.insert(TABLE_CUTSIZE, null, cv1).toInt()
            if (r==-1)
            {
                Toast.makeText(context,"Error!!!",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"Success..",Toast.LENGTH_SHORT).show()
            }

        } catch (e:Exception)
        {
            Toast.makeText(context,"Error= $e",Toast.LENGTH_SHORT).show()
            Log.e(ContentValues.TAG,"Insert Error!!1")
        }
    }
    fun getlastid():Int
    {
        val db=writableDatabase
       /* val q="SELECT MAX($COL_CSM_ID) FROM $TABLEMASTER_CUTSIZE"*/
        val cursor1 = db.query(TABLEMASTER_CUTSIZE,
            arrayOf(COL_CSM_ID), null, null, null, null, null)
        cursor1.moveToLast()
       // val q="SELECT $COL_CSM_ID FROM $TABLEMASTER_CUTSIZE ORDER BY $COL_CSM_ID DESC LIMIT 1"
       // val cursor=db.rawQuery(q, null)
     //  cursor.moveToFirst()
       // while (!cursor.isAfterLast)
      //  {
       //     return cursor.getInt(cursor.getColumnIndex(COL_CSM_ID))
      //  }
       // return cursor.getInt(cursor.getColumnIndex(COL_CSM_ID))
        return cursor1.getInt(cursor1.getColumnIndex(COL_CSM_ID))
    }
    fun getiddetail(id:Int,mCtx: Context):ArrayList<CutSize>{
       // val query="SELECT a.$COL_CSM_NAME,b.$COL_CS_ID,b.$COL_CS_W,b.$COL_CS_CFT FROM $TABLEMASTER_CUTSIZE a,$TABLE_CUTSIZE b where a.$COL_CSM_ID = b.$id"
        val q="SELECT * FROM $TABLE_CUTSIZE WHERE $COL_CSM_ID=$id"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val l=ArrayList<CutSize>()
        if (cursor.count==0)
        {

        }
        else {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val cs = CutSize()
                cs.id = cursor.getInt(cursor.getColumnIndex(COL_CS_ID))
                cs.width = cursor.getDouble(cursor.getColumnIndex(COL_CS_W))
                cs.height = cursor.getDouble(cursor.getColumnIndex(COL_CS_H))
                cs.length = cursor.getDouble(cursor.getColumnIndex(COL_CS_L))
                cs.qty = cursor.getInt(cursor.getColumnIndex(COL_CS_Q))
                cs.cft = cursor.getDouble(cursor.getColumnIndex(COL_CS_CFT))
                cs.mid = cursor.getInt(cursor.getColumnIndex(COL_CSM_ID))
                l.add(cs)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return l
    }
    fun getcutsizelist(mCtx:Context):ArrayList<CutSize>{
       // val query="SELECT a.$COL_CSM_NAME,b.$COL_CS_ID,b.$COL_CS_W,b.$COL_CS_CFT FROM $TABLEMASTER_CUTSIZE a,$TABLE_CUTSIZE b where a.$COL_CSM_ID = b.$COL_CSM_ID"
        val q="SELECT * FROM $TABLEMASTER_CUTSIZE "
      //  val q1="SELECT * FROM $TABLE_CUTSIZE "
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
       // val cursor1=db.rawQuery(q1,null)
        val css=ArrayList<CutSize>()
        if (cursor.count==0)
        {

        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val cs=CutSize()
                cs.pname=cursor.getString(cursor.getColumnIndex(COL_CSM_NAME))
                cs.mid=cursor.getInt(cursor.getColumnIndex(COL_CSM_ID))
                css.add(cs)
                cursor.moveToNext()
            }
          /*  cursor1.moveToFirst()
            while (!cursor1.isAfterLast)
            {
                val cs=CutSize()
                cs.id=cursor1.getInt(cursor1.getColumnIndex(COL_CS_ID))
                cs.width=cursor1.getDouble(cursor1.getColumnIndex(COL_CS_W))
                cs.height=cursor1.getDouble(cursor1.getColumnIndex(COL_CS_H))
                cs.length=cursor1.getDouble(cursor1.getColumnIndex(COL_CS_L))
                cs.qty=cursor1.getInt(cursor1.getColumnIndex(COL_CS_Q))
                cs.cft=cursor1.getDouble(cursor1.getColumnIndex(COL_CS_CFT))
                css.add(cs)
                cursor.moveToNext()
            }*/
        }
        cursor.close()
        db.close()
        return css
    }
    val allList: List<Drum>
        get() {
            val lstdrum = ArrayList<Drum>()
            val q = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor: Cursor = db.rawQuery(q, null)
            if (cursor.moveToFirst()) {
                do {
                    val drum = Drum()
                    drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                    drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                    drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                    drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                    drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                    drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                    drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                    drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                    drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                    drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                    drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                    drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                    drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                    drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                    drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                    drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                    drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                    drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                    drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                    drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                    drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                    drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                    drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                    drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                    drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                    drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                    drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                    lstdrum.add(drum)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstdrum
        }
    fun viewname():ArrayList<String>
    {
        val db=this.writableDatabase
        val q="SELECT $COL_NAME FROM $TABLE_NAME"
        val cursor=db.rawQuery(q,null)
        val n=ArrayList<String>()
        var name=""
        if (cursor.count==0)
        {

        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                 name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            }
            n.add(name)
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return n
    }
    fun updatedrum(id:Int,drum: Drum): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        var result=false
        cv.put(COL_CMM, drum.cmm)
        cv.put(COL_CW, drum.cw)
        cv.put(COL_CQ, drum.cq)
        cv.put(COL_CCFT, drum.ccft)
        cv.put(COL_CkMM, drum.ckmm)
        cv.put(COL_CkW, drum.ckw)
        cv.put(COL_CkQ, drum.ckq)
        cv.put(COL_CkCFT, drum.ckcft)
        cv.put(COL_CDIQ, drum.cdiq)
        cv.put(COL_MMM, drum.mmm)
        cv.put(COL_MW, drum.mw)
        cv.put(COL_MQ, drum.mq)
        cv.put(COL_MCFT, drum.mcft)
        cv.put(COL_SW, drum.sw)
        cv.put(COL_SH, drum.sh)
        cv.put(COL_SL, drum.sl)
        cv.put(COL_SQ, drum.sq)
        cv.put(COL_SCFT, drum.scft)
        cv.put(COL_TCFT, drum.tcft)
        cv.put(COL_RATE, drum.rate)
        cv.put(COL_AMNT, drum.amnt)
        cv.put(COL_DATEDAY,drum.dateday)
        cv.put(COL_DATEMOUNTH,drum.datemounth)
        cv.put(COL_DATEYEAR,drum.dateyear)
        cv.put(COL_DATE,drum.cdate)
        cv.put(COL_INCHMM,drum.inchmm)
        cv.put(COL_NAME,drum.name)
        cv.put(COL_CNO,drum.cno)
        cv.put(COL_VNO,drum.vno)
        try {
             var r=db.update(TABLE_NAME, cv, "$COL_ID=?", arrayOf(id.toString()))
            if(r==1)
            {
                Toast.makeText(context,"Update Successfully",Toast.LENGTH_SHORT).show()
                result=true
            }
            else{
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
            }

        }
        catch (e:Exception)
        {
            Log.e(ContentValues.TAG,"Update Error!!")
            result=false
        }
        return result
    }
    fun deleteDrum(drum:Int):Boolean {
        val q="DELETE FROM $TABLE_NAME WHERE $COL_ID= $drum"
        var db = this.writableDatabase
        var result:Boolean=false
        try {

            val cursor=db.execSQL(q)
            result=true
        }
        catch (e:Exception)
        {
            Log.e(ContentValues.TAG,"Eroor Deleting")
        }
        db.close()
        return result
    }
    fun getvno(mCtx: Context):ArrayList<Drum>{
        val q="SELECT $COL_VNO FROM $TABLE_NAME"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {

        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getname(mCtx: Context):ArrayList<Drum>{
        val q="SELECT $COL_NAME FROM $TABLE_NAME"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            /*Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()*/
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getdrumlist(mCtx:Context):ArrayList<Drum>{
        val q="SELECT * FROM $TABLE_NAME ORDER BY $COL_CNO ASC"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
              drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getdrumlistDESC(mCtx:Context):ArrayList<Drum>{
        val q="SELECT * FROM $TABLE_NAME ORDER BY $COL_CNO DESC"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getdrumlistbyDate(mCtx:Context):ArrayList<Drum>{
        /* val q="SELECT * FROM $TABLE_NAME ORDER BY date($COL_DATE) DESC"*/
        val q="SELECT * FROM $TABLE_NAME ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getdrumlistbyDateASC(mCtx:Context):ArrayList<Drum>{
        val q="SELECT * FROM $TABLE_NAME ORDER BY $COL_DATEYEAR ASC,$COL_DATEMOUNTH ASC , $COL_DATEDAY ASC"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getdrumlistbyNameDESC(mCtx:Context):ArrayList<Drum>{
        val q="SELECT * FROM $TABLE_NAME ORDER BY $COL_NAME DESC"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getdrumlistbyNameASC(mCtx:Context):ArrayList<Drum>{
        val q="SELECT * FROM $TABLE_NAME ORDER BY $COL_NAME ASC"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return drums
    }
    fun getdrumlistbyDateSearchwithChalanASC(mCtx:Context,fy:Int,fm:Int,fd:Int,ty:Int,tm:Int,td:Int):ArrayList<Drum>{
        /*val q="SELECT * FROM $TABLE_NAME WHERE ($COL_DATEYEAR , $COL_DATEMOUNTH,$COL_DATEDAY) BETWEEN ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) AND ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        /*val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATE BETWEEN '01/08/2019' AND '31/08/2019' ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATEYEAR BETWEEN $fy AND $ty AND $COL_DATEMOUNTH BETWEEN $fm AND $tm AND  $COL_DATEDAY BETWEEN $fd AND $td ORDER BY $COL_CNO ASC "
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
            /* Toast.makeText(mCtx,"${cursor.count.toString()} Record found...",Toast.LENGTH_LONG).show()*/
        }
        cursor.close()
        db.close()
        return drums

    }
    fun getdrumlistbyDateSearchwithChalanDESC(mCtx:Context,fy:Int,fm:Int,fd:Int,ty:Int,tm:Int,td:Int):ArrayList<Drum>{
        /*val q="SELECT * FROM $TABLE_NAME WHERE ($COL_DATEYEAR , $COL_DATEMOUNTH,$COL_DATEDAY) BETWEEN ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) AND ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        /*val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATE BETWEEN '01/08/2019' AND '31/08/2019' ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATEYEAR BETWEEN $fy AND $ty AND $COL_DATEMOUNTH BETWEEN $fm AND $tm AND  $COL_DATEDAY BETWEEN $fd AND $td ORDER BY $COL_CNO DESC"
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
            /* Toast.makeText(mCtx,"${cursor.count.toString()} Record found...",Toast.LENGTH_LONG).show()*/
        }
        cursor.close()
        db.close()
        return drums

    }
    fun getdrumlistbyDateSearchDESC(mCtx:Context,fy:Int,fm:Int,fd:Int,ty:Int,tm:Int,td:Int):ArrayList<Drum>{
        /*val q="SELECT * FROM $TABLE_NAME WHERE ($COL_DATEYEAR , $COL_DATEMOUNTH,$COL_DATEDAY) BETWEEN ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) AND ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        /*val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATE BETWEEN '01/08/2019' AND '31/08/2019' ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATEYEAR BETWEEN $fy AND $ty AND $COL_DATEMOUNTH BETWEEN $fm AND $tm AND  $COL_DATEDAY BETWEEN $fd AND $td ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
            /* Toast.makeText(mCtx,"${cursor.count.toString()} Record found...",Toast.LENGTH_LONG).show()*/
        }
        cursor.close()
        db.close()
        return drums

    }
    fun getdrumlistbyDateSearchASC(mCtx:Context,fy:Int,fm:Int,fd:Int,ty:Int,tm:Int,td:Int):ArrayList<Drum>{
        /*val q="SELECT * FROM $TABLE_NAME WHERE ($COL_DATEYEAR , $COL_DATEMOUNTH,$COL_DATEDAY) BETWEEN ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) AND ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        /*val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATE BETWEEN '01/08/2019' AND '31/08/2019' ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATEYEAR BETWEEN $fy AND $ty AND $COL_DATEMOUNTH BETWEEN $fm AND $tm AND  $COL_DATEDAY BETWEEN $fd AND $td ORDER BY $COL_DATEYEAR ASC ,$COL_DATEMOUNTH ASC , $COL_DATEDAY ASC "
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
            /* Toast.makeText(mCtx,"${cursor.count.toString()} Record found...",Toast.LENGTH_LONG).show()*/
        }
        cursor.close()
        db.close()
        return drums

    }
    fun getdrumlistbyDateSearchwithNameASC(mCtx:Context,fy:Int,fm:Int,fd:Int,ty:Int,tm:Int,td:Int):ArrayList<Drum>{
        /*val q="SELECT * FROM $TABLE_NAME WHERE ($COL_DATEYEAR , $COL_DATEMOUNTH,$COL_DATEDAY) BETWEEN ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) AND ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        /*val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATE BETWEEN '01/08/2019' AND '31/08/2019' ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATEYEAR BETWEEN $fy AND $ty AND $COL_DATEMOUNTH BETWEEN $fm AND $tm AND  $COL_DATEDAY BETWEEN $fd AND $td ORDER BY $COL_NAME ASC "
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
            /* Toast.makeText(mCtx,"${cursor.count.toString()} Record found...",Toast.LENGTH_LONG).show()*/
        }
        cursor.close()
        db.close()
        return drums

    }
    fun getdrumlistbyDateSearchwithNameDESC(mCtx:Context,fy:Int,fm:Int,fd:Int,ty:Int,tm:Int,td:Int):ArrayList<Drum>{
        /*val q="SELECT * FROM $TABLE_NAME WHERE ($COL_DATEYEAR , $COL_DATEMOUNTH,$COL_DATEDAY) BETWEEN ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) AND ($COL_DATEYEAR = 2019 , $COL_DATEMOUNTH = 8 , $COL_DATEDAY = 1) ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        /*val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATE BETWEEN '01/08/2019' AND '31/08/2019' ORDER BY $COL_DATEYEAR DESC ,$COL_DATEMOUNTH DESC , $COL_DATEDAY DESC "*/
        val q="SELECT * FROM $TABLE_NAME WHERE $COL_DATEYEAR BETWEEN $fy AND $ty AND $COL_DATEMOUNTH BETWEEN $fm AND $tm AND  $COL_DATEDAY BETWEEN $fd AND $td ORDER BY $COL_NAME DESC "
        val db=this.readableDatabase
        val cursor=db.rawQuery(q,null)
        val drums=ArrayList<Drum>()
        if (cursor.count==0)
        {
            Toast.makeText(mCtx,"No Record Found!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                val drum=Drum()
                drum.id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
                drum.cmm = cursor.getString(cursor.getColumnIndex(COL_CMM)).toInt()
                drum.cw = cursor.getString(cursor.getColumnIndex(COL_CW)).toDouble()
                drum.cq = cursor.getString(cursor.getColumnIndex(COL_CQ)).toInt()
                drum.ccft = cursor.getString(cursor.getColumnIndex(COL_CCFT)).toDouble()
                drum.ckmm = cursor.getString(cursor.getColumnIndex(COL_CkMM)).toInt()
                drum.ckw = cursor.getString(cursor.getColumnIndex(COL_CkW)).toDouble()
                drum.ckq = cursor.getString(cursor.getColumnIndex(COL_CkQ)).toDouble()
                drum.ckcft = cursor.getString(cursor.getColumnIndex(COL_CkCFT)).toDouble()
                drum.cdiq = cursor.getString(cursor.getColumnIndex(COL_CDIQ)).toInt()
                drum.mmm = cursor.getString(cursor.getColumnIndex(COL_MMM)).toInt()
                drum.mw = cursor.getString(cursor.getColumnIndex(COL_MW)).toDouble()
                drum.mq = cursor.getString(cursor.getColumnIndex(COL_MQ)).toInt()
                drum.mcft = cursor.getString(cursor.getColumnIndex(COL_MCFT)).toDouble()
                drum.sw = cursor.getString(cursor.getColumnIndex(COL_SW)).toInt()
                drum.sh = cursor.getString(cursor.getColumnIndex(COL_SH)).toDouble()
                drum.sl = cursor.getString(cursor.getColumnIndex(COL_SL)).toDouble()
                drum.sq = cursor.getString(cursor.getColumnIndex(COL_SQ)).toInt()
                drum.scft = cursor.getString(cursor.getColumnIndex(COL_SCFT)).toDouble()
                drum.tcft = cursor.getString(cursor.getColumnIndex(COL_TCFT)).toDouble()
                drum.rate = cursor.getString(cursor.getColumnIndex(COL_RATE)).toInt()
                drum.amnt = cursor.getString(cursor.getColumnIndex(COL_AMNT)).toInt()
                drum.dateday=cursor.getString(cursor.getColumnIndex(COL_DATEDAY)).toInt()
                drum.datemounth=cursor.getString(cursor.getColumnIndex(COL_DATEMOUNTH)).toInt()
                drum.dateyear=cursor.getString(cursor.getColumnIndex(COL_DATEYEAR)).toInt()
                drum.cdate=cursor.getString(cursor.getColumnIndex(COL_DATE))
                drum.inchmm=cursor.getString(cursor.getColumnIndex(COL_INCHMM))
                drum.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                drum.cno=cursor.getString(cursor.getColumnIndex(COL_CNO)).toInt()
                drum.vno=cursor.getString(cursor.getColumnIndex(COL_VNO))
                drums.add(drum)
                cursor.moveToNext()
            }
            /* Toast.makeText(mCtx,"${cursor.count.toString()} Record found...",Toast.LENGTH_LONG).show()*/
        }
        cursor.close()
        db.close()
        return drums

    }
    fun backup(outFileName: String,i:Int) {

        //database path
        val inFileName = mContext.getDatabasePath(DATABASE_NAME).toString()

        try {

            val dbFile = File(inFileName)
            val fis = FileInputStream(dbFile)

            // Open the empty db as the output stream
            val output = FileOutputStream(outFileName)

            // Transfer bytes from the input file to the output file
            val buffer = ByteArray(1024)
            var length: Int = -1
            while (fis.read(buffer).also { length = it } != -1)
            {
                output.write(buffer, 0, length)
            }
            /*while ((length = fis.read(buffer))!=-1) {
                output.write(buffer, 0, length)
            }*/

            // Close the streams
            output.flush()
            output.close()
            fis.close()
           // Toast.makeText(mContext,inFileName,Toast.LENGTH_LONG).show()
            if (i==1) {
                Toast.makeText(mContext, "Backup Completed", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(mContext, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
    fun importDB(inFileName: String) {

        val outFileName = mContext.getDatabasePath(DATABASE_NAME).toString()

        try {

            val dbFile = File(inFileName)
            val fis = FileInputStream(dbFile)

            // Open the empty db as the output stream
            val output = FileOutputStream(outFileName)

            // Transfer bytes from the input file to the output file
            val buffer = ByteArray(1024)
            var length: Int
            while (fis.read(buffer).also { length = it } != -1)
            {
                output.write(buffer, 0, length)
            }

            // Close the streams
            output.flush()
            output.close()
            fis.close()

            Toast.makeText(mContext, "Import Completed", Toast.LENGTH_SHORT).show()


        } catch (e: Exception) {
            Toast.makeText(mContext, "Unable to import database. Retry", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }
    fun getDatabaseVersion(): Int {
        return DATABASE_VERSION
    }
    fun deletecsdetail(id:Int):Boolean
    {
        val q="DELETE FROM $TABLE_CUTSIZE WHERE $COL_CS_ID= $id"
        var db = this.writableDatabase
        var result:Boolean=false
        try {

            val cursor=db.execSQL(q)
            result=true
        }
        catch (e:Exception)
        {
            Log.e(ContentValues.TAG,"Eroor Deleting")
        }
        db.close()
        return result
    }
    fun deletecsparty(id:Int):Boolean
    {
        val q="DELETE FROM $TABLEMASTER_CUTSIZE WHERE $COL_CSM_ID= $id"
        var db = this.writableDatabase
        var result:Boolean=false
        try {

            val cursor=db.execSQL(q)
            result=true
        }
        catch (e:Exception)
        {
            Log.e(ContentValues.TAG,"Eroor Deleting")
        }
        db.close()
        return result
    }
    fun editcsdetail(id:Int,cs:CutSize):Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        var result=false
        cv.put(COL_CS_L, cs.length)
        cv.put(COL_CS_Q, cs.qty)
        cv.put(COL_CS_CFT, cs.cft)

        try {
            var r=db.update(TABLE_CUTSIZE, cv, "$COL_CS_ID=?", arrayOf(id.toString()))
            if(r==1)
            {
                Toast.makeText(context,"Update Successfully",Toast.LENGTH_SHORT).show()
                result=true
            }
            else{
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
            }

        }
        catch (e:Exception)
        {
            Log.e(ContentValues.TAG,"Update Error!!")
            result=false
        }
        return result
    }
    fun editpartyname(id:Int,name:String):Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues()
        var result=false
        cv.put(COL_CSM_NAME, name)
        try {
            var r=db.update(TABLEMASTER_CUTSIZE, cv, "$COL_CSM_ID=?", arrayOf(id.toString()))
            if(r==1)
            {
                Toast.makeText(context,"Update Successfully",Toast.LENGTH_SHORT).show()
                result=true
            }
            else{
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
            }
        }
        catch (e:Exception)
        {
            Log.e(ContentValues.TAG,"Update Error!!")
            result=false
        }
        return result
    }
    }
