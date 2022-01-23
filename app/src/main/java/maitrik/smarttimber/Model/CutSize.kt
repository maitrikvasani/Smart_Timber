package maitrik.smarttimber.Model

import java.io.Serializable

class CutSize :Serializable {
    var pname:String=""
    var mid:Int=0
    var date:String=""
     var id:Int=0
     var subid:Int=0
     var width:Double=0.0
     var height:Double=0.0
     var length:Double=0.0
     var qty:Int=0
     var cft:Double?=0.0
     var cmt:Double?=0.0

    constructor()

    constructor(width: Double, height: Double, length: Double, qty: Int, cft: Double, cmt: Double) {
        this.width = width
        this.height = height
        this.length = length
        this.qty = qty
        this.cft = cft
        this.cmt = cmt
    }

    constructor(
        pname: String,
        date: String
    ) {
        this.pname = pname
        this.date = date
    }

    constructor( width: Double, height: Double, length: Double, qty: Int, cft: Double?,mid: Int,subid:Int) {

        this.width = width
        this.height = height
        this.length = length
        this.qty = qty
        this.cft = cft
        this.mid = mid
        this.subid = subid
    }
    constructor(  length: Double, qty: Int, cft: Double?) {


        this.length = length
        this.qty = qty
        this.cft = cft
    }

}

data class CutSizeCFTMasterModel(var width: Double,var height: Double,var arrItems:ArrayList<CutSize>,var rate:Double)

data class CutSizeSubItemModel(var width : Double, var height: Double,var rate: Double)


class CutSizeMOdel(var id: Int,var width : Double, var height : Double, var arrITems: ArrayList<CutSizeHL>)


class CutSizeHL(var length : Double ,var qty : Int)