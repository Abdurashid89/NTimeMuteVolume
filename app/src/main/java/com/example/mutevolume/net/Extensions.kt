package com.example.mutevolume.net

import android.view.View


fun View.visible(show: Boolean) {
    if (show) View.VISIBLE else View.INVISIBLE
}