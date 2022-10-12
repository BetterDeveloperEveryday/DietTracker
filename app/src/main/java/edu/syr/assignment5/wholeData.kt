package edu.syr.assignment5

class wholeData {

    var id: Int=0

    var member_date: String? = null

    var member_serving_number_Grains: Int=0
    var member_serving_number_Vegetable: Int=0
    var member_serving_number_Fruit: Int=0
    var member_serving_number_Dairy: Int=0
    var member_serving_number_Meat: Int=0
    var member_serving_number_Nuts: Int=0

    constructor(date: String,
                Grains: Int,
                Vegetable: Int,
                Fruit: Int,
                Dairy: Int,
                Meat: Int,
                Nuts: Int){

        this.member_date=date

        this.member_serving_number_Grains=Grains
        this.member_serving_number_Vegetable=Vegetable
        this.member_serving_number_Fruit=Fruit
        this.member_serving_number_Dairy=Dairy
        this.member_serving_number_Meat=Meat
        this.member_serving_number_Nuts=Nuts
    }

}