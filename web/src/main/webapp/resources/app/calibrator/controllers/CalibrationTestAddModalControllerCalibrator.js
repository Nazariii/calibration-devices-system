angular
    .module('employeeModule')
    .controller('CalibrationTestAddModalControllerCalibrator', ['$rootScope', '$scope', '$http', '$log',
        '$modalInstance', 'CalibrationTestServiceCalibrator', 'verification','Upload','$location','$timeout',
        function ($rootScope, $scope, $http, $log, $modalInstance, calibrationTestServiceCalibrator, verification,Upload,
                  $location,$timeout) {


            $scope.calibrationTests = [];


            $scope.verId = $location.search().param;
            /**
             * Resets Test form
             */
            $scope.resetTestForm = function() {
                $scope.$broadcast('show-errors-reset');
                $scope.TestFormData = null;
            };

            /**
             * Calls resetTestForm after the view loaded
             */
            $scope.resetTestForm();

            function getCalibrationTests(){
                calibrationTestServiceCalibrator
                    .getCalibrationTests()
                    .then(function(data){;
                        $scope.calibrationTests = data.calibrationTests;
                    })
            }

            getCalibrationTests();


            /**
             * Saves new test from the form in database.
             * If everything is ok then resets the test
             * form and updates table with tests.
             */
            $scope.saveCalibrationTest = function() {
                calibrationTestServiceCalibrator
                    .saveCalibrationTest($scope.TestFormData,  $scope.verId)
                    .then(function (data) {
                        if (data == 201) {
                            $scope.closeModal();
                            $scope.resetTestForm();
                            $rootScope.onTableHandling();
                        }
                    });
            }

            /**
             * Closes the modal window for adding new
             * test.
             */
            $rootScope.closeModal = function() {
                $modalInstance.close();
            };



         
        }]);
