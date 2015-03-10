'use strict';

angular.module('onion.controllers', [])
  .controller('MeetingsCtrl', function ($scope, Meetings) {
    Meetings.getList({cityId: 1, pageNum: 1}).then(function (list) {
      //console.log('meetings 11111111', list);
      $scope.meetings = list;
    });
  })
  .controller('MeetingCtrl', function ($scope, $stateParams, $ionicModal, Meetings) {
    Meetings.one($stateParams.meetingId).get().then(function (one) {
      //console.log('meetings 222222', one.meeting);
      $scope.meeting = one.meeting;
    });

    $scope.bookMeeting = function() {
      $scope.hashtagValue = 'blackandwhitephotography'; // if selected, it'll display this value

      $ionicModal.fromTemplateUrl('templates/meeting-book-modal.html', {
        scope: $scope,
        animation: 'slide-in-up'
      }).then(function(modal) {
        $scope.modal = modal;
        $scope.modal.show();
      });
    };
    $scope.openModal = function() {
      $scope.modal.show();
    };
    $scope.closeModal = function() {
      $scope.modal.hide();
    };
    $scope.$on('$destroy', function() {
      $scope.modal.remove();
    });
    $scope.$on('modal.hidden', function() {
      // Execute action
      console.log('modal hidden');
    });
    $scope.$on('modal.removed', function() {
      // Execute action
      console.log('modal removed');
    });
    $scope.doBook = function(u) {
      //$scope.bookmeeting.meetingId = $stateParams.meetingId;
      //$scope.bookmeeting.calenderId = $stateParams.meetingId;
      //$scope.bookmeeting.locationId = $stateParams.meetingId;
      //$scope.bookmeeting.memo = $stateParams.meetingId;
      //
      //Meetings.put()
      
      $scope.contacts.push({ name: u.firstName + ' ' + u.lastName });
      $scope.modal.hide();
    };
  })


  //.controller('MeetingsCtrl', function ($scope, MeetingFactory) {
  //  var result = MeetingFactory.get({cityId: 1, token: '777'}, function () {
  //    $scope.meetings = result.payload.meetings;
  //  });
  //})
  //.controller('MeetingCtrl', function ($scope, $stateParams, $ionicModal, MeetingFactory) {
  //  $ionicModal.fromTemplateUrl('templates/login.html', function (modal) {
  //      $scope.loginModal = modal;
  //    },
  //    {
  //      scope: $scope,
  //      animation: 'slide-in-up',
  //      focusFirstInput: true
  //    }
  //  );
  //  //Be sure to cleanup the modal by removing it from the DOM
  //  $scope.$on('$destroy', function () {
  //    $scope.loginModal.remove();
  //  });
  //
  //  var result = MeetingFactory.get({meetingId: $stateParams.meetingId, token: '777'}, function () {
  //    //console.log('meetings 11111111', result);
  //    $scope.meeting = result.payload.meeting;
  //  });
  //})

  //.controller('AppCtrl', function($scope, $state, $ionicModal) {
  //
  //  $ionicModal.fromTemplateUrl('templates/login.html', function(modal) {
  //      $scope.loginModal = modal;
  //    },
  //    {
  //      scope: $scope,
  //      animation: 'slide-in-up',
  //      focusFirstInput: true
  //    }
  //  );
  //  //Be sure to cleanup the modal by removing it from the DOM
  //  $scope.$on('$destroy', function() {
  //    $scope.loginModal.remove();
  //  });
  //})


  .controller('LoginCtrl', function ($scope, $http, $state, AuthenticationService) {
    $scope.message = "";

    $scope.user = {
      username: null,
      password: null
    };

    $scope.login = function () {
      AuthenticationService.login($scope.user);
    };

    $scope.$on('event:auth-loginRequired', function (e, rejection) {
      $scope.loginModal.show();
    });

    $scope.$on('event:auth-loginConfirmed', function () {
      $scope.username = null;
      $scope.password = null;
      $scope.loginModal.hide();
    });

    $scope.$on('event:auth-login-failed', function (e, status) {
      var error = "Login failed.";
      if (status == 401) {
        error = "Invalid Username or Password.";
      }
      $scope.message = error;
    });

    $scope.$on('event:auth-logout-complete', function () {
      console.log("logout complete");
    });
  })
  .controller('LogoutCtrl', function ($scope, AuthenticationService) {
    AuthenticationService.logout();
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
