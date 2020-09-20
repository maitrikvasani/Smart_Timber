package maitrik.smarttimber.Model

class Drum {
    var id: Int = 0
    var cmm: Int = 0
    var cw: Double = 0.0
    var cq: Int = 0
    var ccft: Double = 0.0
    var ckmm: Int? = 0
    var ckw: Double? = 0.0
    var ckq: Double? = 0.0
    var ckcft: Double? = 0.0
    var cdiq: Int? = 0
    var mmm: Int? = 0
    var mw: Double? = 0.0
    var mq: Int? = 0
    var mcft: Double? = 0.0
    var sw: Int? = 0
    var sh: Double? = 0.0
    var sl: Double? = 0.0
    var sq: Int? = 0
    var scft: Double? = 0.0
    var tcft: Double? = 0.0
    var rate: Int? = 0
    var amnt: Int? = 0
    var dateday:Int=0
    var datemounth:Int=0
    var dateyear:Int=0
    var cdate:String=""
    var inchmm:String?=""
    var name:String?=""
    var cno:Int?=0
    var vno:String?=""

    constructor(){

    }

    constructor(
        cmm: Int,
        cw: Double,
        cq: Int,
        ccft: Double,
        ckmm: Int?,
        ckw: Double?,
        ckq: Double?,
        ckcft: Double?,
        cdiq: Int?,
        mmm: Int?,
        mw: Double?,
        mq: Int?,
        mcft: Double?,
        sw: Int?,
        sh: Double?,
        sl: Double?,
        sq: Int?,
        scft: Double?,
        tcft: Double?,
        rate: Int?,
        amnt: Int?,
        dateday: Int,
        datemounth: Int,
        dateyear: Int,
        cdate:String,
        inchmm: String?,
        name: String?,
        cno: Int?,
        vno: String?
    ) {
        this.cmm = cmm
        this.cw = cw
        this.cq = cq
        this.ccft = ccft
        this.ckmm = ckmm
        this.ckw = ckw
        this.ckq = ckq
        this.ckcft = ckcft
        this.cdiq = cdiq
        this.mmm = mmm
        this.mw = mw
        this.mq = mq
        this.mcft = mcft
        this.sw = sw
        this.sh = sh
        this.sl = sl
        this.sq = sq
        this.scft = scft
        this.tcft = tcft
        this.rate = rate
        this.amnt = amnt
        this.dateday = dateday
        this.datemounth = datemounth
        this.dateyear = dateyear
        this.cdate= cdate
        this.inchmm = inchmm
        this.name = name
        this.cno = cno
        this.vno = vno
    }
}