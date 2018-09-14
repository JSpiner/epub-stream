import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class EpubStream(val file : File) {

    fun unzip() : Completable {
        return Completable.complete()
    }

    fun getMimeType() : Single<Any> {
        return Single.just(1)
    }

    fun getContainer() : Single<Any> {
        return Single.just(1)
    }

    fun getOpf() : Single<Any> {
        return Single.just(1)
    }

    fun getToc() : Single<Any> {
        return Single.just(1)
    }

    fun getFile() : Single<Any> {
        return Single.just(1)
    }

}