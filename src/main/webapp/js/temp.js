app.controller('SelectClassController', function($scope,$http,$location,$routeParams) {
  $scope.warnings = [];
  $scope.characterClassMap = {};
  $http.get('/api/classes').then(function(response) {
    $scope.characterClassMap = response.data;
  });
  console.log($routeParams.characterId);
  var characterClass = {};
  //possible classes
  $http.get('/api/character/'+$routeParams.characterId + '/select-class').then(function(response) {
    $scope.selectClass = response.data;
  });
  //current character
  $http.get('/api/character/'+$routeParams.characterId).then(function(response) {
    $scope.character = response.data;
  });


  $scope.addClass = function(value) {
    console.log(value);
  };

  $scope.put = function (character) {
    $http.put('/api/savecharacter', character).then(function(response) {
      $scope.character = response.data;
    });
  };
  $scope.go = function ( path ) {
    $location.path( path );
  };
});
