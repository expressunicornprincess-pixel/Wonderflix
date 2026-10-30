package com.wonderflix.app

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : Activity() {
    private val navy = Color.rgb(16, 24, 45)
    private val panel = Color.rgb(33, 60, 98)
    private val blue = Color.rgb(36, 121, 216)
    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var status: TextView

    // This is a public Blender demo movie for testing the player.
    // Replace it only with a video you own or are licensed to stream.
    private val demoUrl =
        "https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = navy
        window.navigationBarColor = navy
        buildScreen()
    }

    private fun buildScreen() {
        val scroll = ScrollView(this).apply { setBackgroundColor(navy) }
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(16), dp(18), dp(22))
        }
        scroll.addView(page)
        setContentView(scroll)

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val brand = TextView(this).apply {
            text = "WonderFlix"
            textSize = 29f
            setTextColor(Color.WHITE)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        header.addView(brand, LinearLayout.LayoutParams(0, -2, 1f))
        header.addView(label("◉", 25f, Color.WHITE))
        page.addView(header)
        page.addView(label("MOVIES FOR EVERY ADVENTURE", 11f, 0xFFA8C8FF.toInt()))

        val feature = card()
        feature.addView(label("🌊", 64f, Color.WHITE).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(20), 0, dp(12))
        })
        feature.addView(label("FEATURED MOVIE", 11f, 0xFFA8C8FF.toInt()))
        feature.addView(label("LUCA", 32f, Color.WHITE, true))
        feature.addView(label("Friendship  •  Adventure  •  Animation", 14f, 0xFFD7EEFF.toInt()))
        feature.addView(label(
            "Discover the magical seaside adventure of Luca and Alberto.",
            14f, Color.WHITE
        ))
        feature.addView(label(
            "Luca is featured here. A licensed video source is needed before the full film can stream.",
            12f, 0xFFD7EEFF.toInt()
        ))
        page.addView(feature, marginParams(top = 18))

        page.addView(label("Movie player", 21f, Color.WHITE, true), marginParams(top = 20))
        val playerPanel = card()
        playerView = PlayerView(this).apply {
            useController = true
            setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
            setBackgroundColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(220)
            )
        }
        playerPanel.addView(playerView)
        status = label("Ready to test. Press Play test film.", 13f, 0xFFD7EEFF.toInt())
        playerPanel.addView(status, marginParams(top = 10))
        val play = Button(this).apply {
            text = "▶  Play free test film"
            setTextColor(Color.WHITE)
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(blue))
            setOnClickListener { startDemo() }
        }
        playerPanel.addView(play, marginParams(top = 10))
        page.addView(playerPanel, marginParams(top = 10))

        page.addView(label("Popular movies", 21f, Color.WHITE, true), marginParams(top = 20))
        val grid = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val row1 = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        row1.addView(movieTile("🌊", "Luca", "Featured"), LinearLayout.LayoutParams(0, -2, 1f))
        row1.addView(movieTile("🐻", "Paddington", "Coming soon"), LinearLayout.LayoutParams(0, -2, 1f))
        val row2 = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        row2.addView(movieTile("🌳", "The Gruffalo", "Coming soon"), LinearLayout.LayoutParams(0, -2, 1f))
        row2.addView(movieTile("🐉", "Dragon Adventure", "Coming soon"), LinearLayout.LayoutParams(0, -2, 1f))
        grid.addView(row1)
        grid.addView(row2)
        page.addView(grid, marginParams(top = 8))

        val favourites = card()
        favourites.addView(label("My favourites ⭐", 18f, Color.WHITE, true))
        favourites.addView(label("Your saved movies will appear here.", 13f, 0xFFD7EEFF.toInt()))
        val save = Button(this).apply {
            text = "♡  Add Luca to favourites"
            setTextColor(Color.WHITE)
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF294A75.toInt()))
            setOnClickListener { text = "♥  Luca saved" }
        }
        favourites.addView(save)
        page.addView(favourites, marginParams(top = 18))
        page.addView(label("⌂  Home          ⌕  Search          ♡  Favourites          ☻  Profile",
            13f, Color.WHITE).apply { gravity = Gravity.CENTER }, marginParams(top = 22))
    }

    private fun startDemo() {
        status.text = "Loading the free test film…"
        if (player == null) {
            player = ExoPlayer.Builder(this).build().also { exo ->
                playerView.player = exo
                exo.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        status.text = when (playbackState) {
                            Player.STATE_BUFFERING -> "Buffering video…"
                            Player.STATE_READY -> "Video ready. Playing the test film."
                            Player.STATE_ENDED -> "Test film finished."
                            else -> status.text
                        }
                    }
                    override fun onPlayerError(error: PlaybackException) {
                        status.text = "The video host could not provide the film. This may be a host or network restriction, not your Wi-Fi."
                    }
                })
            }
        }
        player?.setMediaItem(MediaItem.fromUri(demoUrl))
        player?.prepare()
        player?.playWhenReady = true
    }

    private fun movieTile(emoji: String, title: String, subtitle: String): View {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(7), dp(7), dp(7), dp(7))
            setBackgroundColor(panel)
        }
        box.addView(label(emoji, 48f, Color.WHITE).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(17), 0, dp(17))
            setBackgroundColor(0xFF294A75.toInt())
        })
        box.addView(label(title, 15f, Color.WHITE, true))
        box.addView(label(subtitle, 11f, 0xFFD7EEFF.toInt()))
        return box
    }

    private fun card() = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(14), dp(13), dp(14), dp(13))
        setBackgroundColor(panel)
    }

    private fun label(
        textValue: String, size: Float, color: Int, bold: Boolean = false
    ) = TextView(this).apply {
        text = textValue
        textSize = size
        setTextColor(color)
        if (bold) setTypeface(null, android.graphics.Typeface.BOLD)
        setPadding(0, dp(3), 0, dp(3))
    }

    private fun marginParams(top: Int = 0) =
        LinearLayout.LayoutParams(-1, -2).apply { topMargin = dp(top) }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        playerView.player = null
        player?.release()
        player = null
        super.onDestroy()
    }
}
