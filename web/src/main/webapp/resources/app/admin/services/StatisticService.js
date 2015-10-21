angular
    .module('adminModule')
    .factory('StatisticService', function ($http) {
        var getData = function (type) {
            return $http.get('/admin/statistics/' + type)
                .then(function(result) {
                    return result.data;
                });
        };
        return {
            organizations: function() { return getData('organizations'); },
            users: function() { return getData('users'); },
            devices: function() { return getData('device-category'); },
            verifications: function() { return getData('verifications'); },
            employee: function() { return getData('employee'); }
        }
    });
