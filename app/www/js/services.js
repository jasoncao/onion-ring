'use strict';

angular.module('onion.services', ['ngResource', 'http-auth-interceptor', 'restangular'])
  .config(function (RestangularProvider) {
    RestangularProvider.setBaseUrl('http://localhost:8080/api/v1');
    RestangularProvider.setDefaultHttpFields({cache: true});

    RestangularProvider.addResponseInterceptor(function (data, operation, what, url, response, deferred) {
      if (operation === "getList") {
        return data.meetings; //TODO: maybe need to unified the list for the sake of simplicity, as "list"
      }
      return data;
    });
  })
  .factory('Meetings', function (Restangular) {
    return Restangular.service('meetings');
  })

  
  
  
  

  .factory('Chats', function () {
    // Might use a resource here that returns a JSON array

    // Some fake testing data
    var chats = [{
      id: 0,
      name: 'Ben Sparrow',
      lastText: 'You on your way?',
      face: 'https://pbs.twimg.com/profile_images/514549811765211136/9SgAuHeY.png'
    }, {
      id: 1,
      name: 'Max Lynx',
      lastText: 'Hey, it\'s me',
      face: 'https://avatars3.githubusercontent.com/u/11214?v=3&s=460'
    }, {
      id: 2,
      name: 'Andrew Jostlin',
      lastText: 'Did you get the ice cream?',
      face: 'https://pbs.twimg.com/profile_images/491274378181488640/Tti0fFVJ.jpeg'
    }, {
      id: 3,
      name: 'Adam Bradleyson',
      lastText: 'I should buy a boat',
      face: 'https://pbs.twimg.com/profile_images/479090794058379264/84TKj_qa.jpeg'
    }, {
      id: 4,
      name: 'Perry Governor',
      lastText: 'Look at my mukluks!',
      face: 'https://pbs.twimg.com/profile_images/491995398135767040/ie2Z_V6e.jpeg'
    }];

    return {
      all: function () {
        return chats;
      },
      remove: function (chat) {
        chats.splice(chats.indexOf(chat), 1);
      },
      get: function (chatId) {
        for (var i = 0; i < chats.length; i++) {
          if (chats[i].id === parseInt(chatId)) {
            return chats[i];
          }
        }
        return null;
      }
    }
  })

  .factory('Friends', function () {
    // Might use a resource here that returns a JSON array

    // Some fake testing data
    // Some fake testing data
    var friends = [{
      id: 0,
      name: 'Ben Sparrow',
      notes: 'Enjoys drawing things',
      face: 'https://pbs.twimg.com/profile_images/514549811765211136/9SgAuHeY.png'
    }, {
      id: 1,
      name: 'Max Lynx',
      notes: 'Odd obsession with everything',
      face: 'https://avatars3.githubusercontent.com/u/11214?v=3&s=460'
    }, {
      id: 2,
      name: 'Andrew Jostlen',
      notes: 'Wears a sweet leather Jacket. I\'m a bit jealous',
      face: 'https://pbs.twimg.com/profile_images/491274378181488640/Tti0fFVJ.jpeg'
    }, {
      id: 3,
      name: 'Adam Bradleyson',
      notes: 'I think he needs to buy a boat',
      face: 'https://pbs.twimg.com/profile_images/479090794058379264/84TKj_qa.jpeg'
    }, {
      id: 4,
      name: 'Perry Governor',
      notes: 'Just the nicest guy',
      face: 'https://pbs.twimg.com/profile_images/491995398135767040/ie2Z_V6e.jpeg'
    }];

    return {
      all: function () {
        return friends;
      },
      get: function (friendId) {
        // Simple index lookup
        return friends[friendId];
      }
    }
  })


  //.factory('AuthenticationService', function ($rootScope, $http, authService, $httpBackend) {
  //  return {
  //    login: function (user) {
  //      $http.post('https://login', {user: user}, {ignoreAuthModule: true})
  //        .success(function (data, status, headers, config) {
  //
  //          $http.defaults.headers.common.Authorization = data.authorizationToken;  // Step 1
  //
  //          // Need to inform the http-auth-interceptor that
  //          // the user has logged in successfully.  To do this, we pass in a function that
  //          // will configure the request headers with the authorization token so
  //          // previously failed requests(aka with status == 401) will be resent with the
  //          // authorization token placed in the header
  //          authService.loginConfirmed(data, function (config) {  // Step 2 & 3
  //            config.headers.Authorization = data.authorizationToken;
  //            return config;
  //          });
  //        })
  //        .error(function (data, status, headers, config) {
  //          $rootScope.$broadcast('event:auth-login-failed', status);
  //        });
  //    },
  //    logout: function (user) {
  //      $http.post('https://logout', {}, {ignoreAuthModule: true})
  //        .finally(function (data) {
  //          delete $http.defaults.headers.common.Authorization;
  //          $rootScope.$broadcast('event:auth-logout-complete');
  //        });
  //    },
  //    loginCancelled: function () {
  //      authService.loginCancelled();
  //    }
  //  };
  //})


  //.factory('MeetingFactory', function ($resource, SERVER_PATH) {
  //  return $resource(SERVER_PATH + '/meetings/:meetingId', {}, factoryActions);
  //});

//var factoryActions = {
//  'get': {
//    method: 'GET',
//    headers: {
//      'Accept': 'application/json'
//    },
//    responseType: 'json'
//  },
//  'save': {
//    method: 'POST',
//    headers: {
//      'Accept': 'application/json',
//      'Content-Type': 'application/json'
//    },
//    responseType: 'json'
//  },
//  'create': {
//    method: 'PUT',
//    headers: {
//      'Accept': 'application/json',
//      'Content-Type': 'application/json'
//    },
//    responseType: 'json'
//  },
//  'delete': {
//    method: 'DELETE',
//    headers: {
//      'Accept': 'application/json',
//      'Content-Type': 'application/json'
//    },
//    responseType: 'json'
//  }
//};
