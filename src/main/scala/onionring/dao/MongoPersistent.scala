package onionring.dao

import com.mongodb.casbah.MongoClient

/**
 * Created by famo on 1/25/15.
 */
object MongoPersistent {
  val client = MongoClient("127.0.0.1")
  val onionRingDB = client("onion_ring")
}
