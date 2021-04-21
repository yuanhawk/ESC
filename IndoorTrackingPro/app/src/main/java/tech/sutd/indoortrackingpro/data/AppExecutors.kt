package tech.sutd.indoortrackingpro.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutors(private val diskIO: Executor, private val mainThread: Executor,
                   private val networkIO: Executor
) {
    fun getDiskIO(): Executor {
        return diskIO
    }

    fun getMainThread(): Executor {
        return mainThread
    }

    fun getNetworkIO(): Executor {
        return networkIO
    }

    private class MainThreadExecutor : Executor {
        private val handler: Handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            if (command != null) {
                handler.post(command)
            }
        }
    }

    companion object {
        var instance: AppExecutors? = null
            get() {
                if (field == null) {
                    synchronized(LOCK) {
                        field = AppExecutors(
                            Executors.newSingleThreadExecutor(),
                            Executors.newFixedThreadPool(threadCt),
                            MainThreadExecutor()
                        )
                    }
                }
                return field
            }
            private set
        private val LOCK = Any()
        private val threadCt = Runtime.getRuntime().availableProcessors() + 1
    }

}