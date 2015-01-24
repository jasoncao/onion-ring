var restify = require('restify');
var server = restify.createServer();
server.listen(8080, function () {
  console.log('%s listening at %s', server.name, server.url);
});

server.get('/meetings', function create(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  res.send(meetings);
  return next();
});

server.get('/meetings/:meetingId', function create(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  res.send(meeting[req.params.meetingId]);
  return next();
});

var meetings = {
  "header": {},
  "payload": {
    "paging": {
      "offset": ""
    },
    "meetings": [
      {
        "id": 0,
        "subject": "如何快速致富!!",
        "desc": "跟我吃饭吧，我将教你如何快速致富，我的屌丝可以复制。",
        "price": 100,
        "createTime": "2015-01-01",
        "updateTime": "2015-01-01",
        "sellerUser": {
          "id": 0,
          "name": "莫凡",
          "title": "专业屌丝",
          "icon": "http://img5q.duitang.com/uploads/item/201401/27/20140127083443_RX2da.jpeg",
          "rating": 1
        }
      }, {
        "id": 1,
        "subject": "如何减肥成功!!",
        "desc": "跟我吃饭吧，我将教你如何减肥成功，你也可以像我一样瘦。",
        "price": 200,
        "createTime": "2015-01-02",
        "updateTime": "2015-01-02",
        "sellerUser": {
          "id": 1,
          "name": "静静",
          "title": "美容院院长",
          "icon": "http://p1.qqyou.com/pic/UploadPic/2014-12/22/2014122206012853199.jpg",
          "rating": 2
        }
      }
    ]
  }
};

var meeting = [
  {
    "header": {},
    "payload": {
      "meeting": {
        "id": 0,
        "subject": "如何快速致富!!",
        "desc": "跟我吃饭吧，我将教你如何快速致富，我的屌丝可以复制。",
        "price": 100,
        "createTime": "2015-01-01",
        "updateTime": "2015-01-01",
        "calender": [
          {
            "id": 0,
            "fromTime": "18:00",
            "toTime": "20:00",
            "frequency": "every_friday"
          }
        ],
        "location": [
          {
            "id": 0,
            "address": "三里屯"
          }
        ],
        "sellerUser": {
          "id": 0,
          "name": "莫凡",
          "title": "专业屌丝",
          "icon": "http://img5q.duitang.com/uploads/item/201401/27/20140127083443_RX2da.jpeg",
          "rating": 1
        }
      }
    }
  }, {
    "header": {},
    "payload": {
      "meeting": {
        "id": 1,
        "subject": "如何减肥成功!!",
        "desc": "跟我吃饭吧，我将教你如何减肥成功，你也可以像我一样瘦。",
        "price": 200,
        "createTime": "2015-01-02",
        "updateTime": "2015-01-02",
        "calender": [
          {
            "id": 0,
            "fromTime": "18:50",
            "toTime": "21:00",
            "frequency": "everyday"
          }
        ],
        "location": [
          {
            "id": 0,
            "address": "田子坊"
          }
        ],
        "sellerUser": {
          "id": 1,
          "name": "静静",
          "title": "美容院院长",
          "icon": "http://p1.qqyou.com/pic/UploadPic/2014-12/22/2014122206012853199.jpg",
          "rating": 2
        }
      }
    }
  }
];
