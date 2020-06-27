package com.davidbronn.personalimdb.ui.screens

import android.view.View
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.progress.KProgressBar
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.davidbronn.personalimdb.R
import org.hamcrest.Matcher

/**
 * Created by Jude on 25/June/2020
 */
class SearchActivityScreen : Screen<SearchActivityScreen>() {

    val edMovies: KEditText = KEditText { withId(R.id.edMovies) }
    val rvMovies: KRecyclerView =
        KRecyclerView({ withId(R.id.rvMovies) }, itemTypeBuilder = { itemType(::ViewHolderItem) })
    val kProgressBar: KProgressBar = KProgressBar { withId(R.id.progress_circular) }
}

class ViewHolderItem(parent: Matcher<View>) : KRecyclerItem<ViewHolderItem>(parent) {
    val tvTitle = KTextView(parent) { withId(R.id.tvTitle) }
    val sIvPoster = KImageView(parent) { withId(R.id.sIvPoster) }
}