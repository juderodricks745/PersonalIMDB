package com.davidbronn.personalimdb.ui.screens

import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.davidbronn.personalimdb.R

/**
 * Created by Jude on 26/June/2020
 */
class SplashActivityScreen : Screen<SplashActivityScreen>() {

    val lblPersonalIMDB: KTextView = KTextView { withId(R.id.lblPersonalIMDB) }
}