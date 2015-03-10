var restify = require('restify');
var server = restify.createServer();
//server.on('after', restify.auditLogger({
//  log: bunyan.createLogger({
//    name: 'audit',
//    stream: process.stdout
//  })
//}));

server.listen(8080, function () {
  console.log('%s listening at %s', server.name, server.url);
});

server.get('/api/v1/meetings', function create(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  res.send(meetings);

  console.log("request - Accept:", req.header("Accept"));
  console.log("request - Content-Type:", req.header("Content-Type"));
  console.log("request - url:", req.url);
  console.log("request - method:", req.method);
  console.log("-----------------");
  console.log("response - Accept:", res.header("Accept"));
  console.log("response - Content-Type:", res.header("Content-Type"));
  console.log("=============================================");
  return next();
});

server.get('/api/v1/meetings/:meetingId', function create(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "X-Requested-With");
  res.send(meeting[req.params.meetingId]);

  console.log("request - Accept:", req.header("Accept"));
  console.log("request - Content-Type:", req.header("Content-Type"));
  console.log("request - url:", req.url);
  console.log("request - method:", req.method);
  console.log("-----------------");
  console.log("response - Accept:", res.header("Accept"));
  console.log("response - Content-Type:", res.header("Content-Type"));
  console.log("=============================================");
  return next();
});

var meetings = {
  "meetings": [
    {
      "id": "0",
      "cityId": 24,
      "subject": "如何快速致富!!",
      "target": "学生",
      "description": "跟我吃饭吧，我将教你如何快速致富，我的屌丝可以复制。",
      "price": 100,
      "createTime": "2015-01-01",
      "updateTime": "2015-01-01",
      "seller": {
        "id": 0,
        "name": "莫凡",
        "title": "专业屌丝",
        "icon": "http://img5q.duitang.com/uploads/item/201401/27/20140127083443_RX2da.jpeg",
        "rating": 1
      }
    }, {
      "id": "1",
      "cityId": 24,
      "subject": "如何减肥成功!!",
      "target": "胖子",
      "description": "跟我吃饭吧，我将教你如何减肥成功，你也可以像我一样瘦。",
      "price": 200,
      "createTime": "2015-01-02",
      "updateTime": "2015-01-02",
      "seller": {
        "id": 1,
        "name": "静静",
        "title": "美容院院长",
        "icon": "http://p1.qqyou.com/pic/UploadPic/2014-12/22/2014122206012853199.jpg",
        "rating": 2
      }
    }
  ]
};

var meeting = [
  {
    "meeting": {
      "id": 0,
      "cityId": 24,
      "subject": "如何快速致富!!",
      "target": "学生",
      "description": "跟我吃饭吧，我将教你如何快速致富，我的屌丝可以复制。",
      "price": 100,
      "createTime": "2015-01-01",
      "updateTime": "2015-01-01",
      "seller": {
        "id": 0,
        "name": "莫凡",
        "title": "专业屌丝",
        "icon": "http://img5q.duitang.com/uploads/item/201401/27/20140127083443_RX2da.jpeg",
        "rating": 1
      },
      "selection": [
        {
          "id": 0,
          "time": "2015/01/02 20:00",
          "duration": "2",
          "address": "三里屯",
          "gps": "123123"
        }
      ],
      "comments": [
        {
          "user": {
            "id": 0,
            "name": "莫凡1",
            "icon": "http://img5q.duitang.com/uploads/item/201401/27/20140127083443_RX2da.jpeg"
          },
          "rating": 3,
          "content": "hahahah"
        }
      ]
    }
  }, {
    "meeting": {
      "id": 1,
      "cityId": 24,
      "subject": "如何减肥成功!!",
      "target": "胖子",
      "description": "跟我吃饭吧，我将教你如何减肥成功，你也可以像我一样瘦。",
      "price": 200,
      "createTime": "2015-01-02",
      "updateTime": "2015-01-02",
      "seller": {
        "id": 1,
        "name": "静静",
        "title": "美容院院长",
        "icon": "http://p1.qqyou.com/pic/UploadPic/2014-12/22/2014122206012853199.jpg",
        "rating": 2
      },
      "selection": [
        {
          "id": 0,
          "time": "2015/01/02 20:00",
          "duration": "2",
          "address": "三里屯",
          "gps": "123123"
        }
      ],
      "comments": [
        {
          "user": {
            "id": 0,
            "name": "莫凡2",
            "icon": "http://img5q.duitang.com/uploads/item/201401/27/20140127083443_RX2da.jpeg"
          },
          "rating": 4,
          "content": "hehehehe"
        }
      ]
    }
  }
];
