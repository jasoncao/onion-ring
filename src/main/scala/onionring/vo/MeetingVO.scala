package onionring.vo

import spray.json.DefaultJsonProtocol

/**
 * Created by famo on 1/25/15.
 */

object MeetingVO extends DefaultJsonProtocol {
  implicit val paging = jsonFormat2(Paging)
  implicit val emptyHeader = jsonFormat0(EmptyHeader)
  implicit val meetingListPayload = jsonFormat3(MeetingListRequestPayload)
  implicit val meetingListRequest = jsonFormat2(MeetingListRequest)

  implicit val meetingSummary = jsonFormat6(MeetingSummary)
  implicit val userSummary = jsonFormat5(UserSummary)
  implicit val meetingListResponseMeetingList = jsonFormat2(MeetingListResponseMeetingList)
  implicit val meetingListResponsePayload = jsonFormat2(MeetingListResponsePayload)
  implicit val meetingListResponse = jsonFormat2(MeetingListResponse)
}

case class Paging(offset: Long, limit: Long)


case class EmptyHeader()

case class MeetingListRequestPayload(uid: Long, cityId: Long, paging: Paging)

case class MeetingListRequest(header: EmptyHeader, payload: MeetingListRequestPayload)


case class MeetingSummary(id: Long, subject: String, description: String, price: Double, createTime: String, updateTime: String)

case class UserSummary(id: Long, name: String, title: String, icon: String, rating: Long)

case class MeetingListResponseMeetingList(meetingSummary: MeetingSummary, userSummary: UserSummary)

case class MeetingListResponsePayload(paging: Paging, meetingList: List[MeetingListResponseMeetingList])

case class MeetingListResponse(header: EmptyHeader = EmptyHeader(), payload: MeetingListResponsePayload)
