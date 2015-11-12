'use strict';

angular.module('biDaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


