package com.timothy.common.data


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.common.data.LoadingStatusData
 * @Author: MoTao
 * @Date: 2023-08-29
 * <p>
 * <p/>
 */
sealed class LoadingStatusData {
    abstract val message:String


    class LoadingStart() : LoadingStatusData() {
        override val message: String
            get() = ""

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class LoadingSuccess : LoadingStatusData(){
        override val message: String
            get() = ""

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class LoadingFail(private val errorCode: Int = -1, private val error:String? = null): LoadingStatusData(){
        override val message: String
            get() = "code=$errorCode;error message=$error"

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class LoadingMoreStart: LoadingStatusData() {
        override val message: String
            get() = ""

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class LoadingMoreSuccess: LoadingStatusData() {
        override val message: String
            get() = ""

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class LoadingMoreFail(private val errorCode: Int = -1, private val error:String? = null): LoadingStatusData() {
        override val message: String
            get() = "code=$errorCode;error message=$error"

        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }
}