package com.example.mutevolume.Util

import android.view.View

class Extensions {

   fun View.visible(visisble : Boolean){
       this.visibility = if (visisble) View.VISIBLE else View.GONE
       }
   }