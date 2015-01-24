angular.module('starter.controllers', [])

  .controller('MeetingsCtrl', function ($scope, MeetingFactory) {
    var result = MeetingFactory.get(function () {
      $scope.meetings = result.payload.meetings;
    });
  })
  .controller('MeetingCtrl', function ($scope, $stateParams, MeetingFactory) {
    var result = MeetingFactory.get({meetingId: $stateParams.meetingId}, function () {
      //console.log('meetings 11111111', result);
      $scope.meeting = result.payload.meeting;
    });
  })

  .controller('DashCtrl', function ($scope) {
  })

  .controller('ChatsCtrl', function ($scope, Chats) {
    $scope.chats = Chats.all();
    $scope.remove = function (chat) {
      Chats.remove(chat);
    }
  })
  .controller('ChatDetailCtrl', function ($scope, $stateParams, Chats) {
    $scope.chat = Chats.get($stateParams.chatId);
  })

  .controller('FriendsCtrl', function ($scope, Friends) {
    $scope.friends = Friends.all();
  })
  .controller('FriendDetailCtrl', function ($scope, $stateParams, Friends) {
    $scope.friend = Friends.get($stateParams.friendId);
  })

  .controller('AccountCtrl', function ($scope) {
    $scope.settings = {
      enableFriends: true
    };
  });
