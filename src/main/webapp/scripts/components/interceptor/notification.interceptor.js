 'use strict';

angular.module('biDaApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-biDaApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-biDaApp-params')});
                }
                return response;
            }
        };
    });
