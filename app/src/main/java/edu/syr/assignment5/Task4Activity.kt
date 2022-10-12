package edu.syr.assignment5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class Task4Activity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener, View.OnClickListener {

    private var mVideoId: String? = null
    private val RECOVERY_DIALOG_REQUEST = 1
    private var mPlayer: YouTubePlayer? = null
    private var pause: Button? = null
    private var play: Button? = null
    private var defaultsel: Button? = null
    private var minimalsel: Button? = null
    private var chromelesssel: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task4)

        val arguments = intent.extras
        if (arguments != null && arguments.containsKey(VIDEO_ID)) {
            mVideoId = arguments.getString(VIDEO_ID)
        }
        //button setting
        pause = findViewById(R.id.pause) as Button
        pause!!.setOnClickListener(this)
        play = findViewById(R.id.play) as Button
        play!!.setOnClickListener(this)
        //defaultsel = findViewById(R.id.defaultstyle) as Button
        //defaultsel!!.setOnClickListener(this)
        //minimalsel = findViewById(R.id.minimalstyle) as Button
        //minimalsel!!.setOnClickListener(this)
        //chromelesssel = findViewById(R.id.chromelessstyle) as Button
        //chromelesssel!!.setOnClickListener(this)

        val playerView = findViewById(R.id.youTubePlayerView) as YouTubePlayerView
        playerView.initialize(getString(R.string.google_maps_key), this)

        val tbar: Toolbar?  = findViewById<Toolbar>(R.id.myToolbar_activity4)
        //setSupportActionBar(tbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer, restored: Boolean) {

        mPlayer = youTubePlayer

        //This flag tells the player to switch to landscape when in fullscreen, it will also return to portrait
        //when leaving fullscreen
        mPlayer!!.fullscreenControlFlags = YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION

        //This flag tells the player to automatically enter fullscreen when in landscape. Since we don't have
        //landscape layout for this activity, this is a good way to allow the user rotate the video player.
        mPlayer!!.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE)

        //This flag controls the system UI such as the status and navigation bar, hiding and showing them
        //alongside the player UI
        mPlayer!!.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI)

        if (mVideoId != null) {
            if (restored) {
                mPlayer!!.play()
            } else {
                mPlayer!!.loadVideo(mVideoId)
            }
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider, youTubeInitializationResult: YouTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        } else {
            //Handle the failure
            Toast.makeText(this, "onInitializationFailure", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View) {
        //Null check the player
        if (mPlayer != null) {
            if (v === play) {
                mPlayer!!.play()
            } else if (v === pause) {
                mPlayer!!.pause()
            } else if (v === defaultsel) {
                mPlayer!!.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            } else if (v === chromelesssel) {
                mPlayer!!.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
            } else if (v === minimalsel) {
                mPlayer!!.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
            }
        }
    }

    companion object {
        internal var VIDEO_ID = "video id"
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }*/



    }