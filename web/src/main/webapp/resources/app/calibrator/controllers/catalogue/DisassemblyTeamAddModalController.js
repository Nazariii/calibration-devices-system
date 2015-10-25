angular.module('employeeModule')
    .controller('DisassemblyTeamAddModalControllerCalibrator',
    ['$rootScope', '$scope', '$modalInstance', 'DisassemblyTeamServiceCalibrator', '$modal',
        function ($rootScope, $scope, $modalInstance, DisassemblyTeamServiceCalibrator, $modal) {


            var teamData = {};
            /**
             * Closes modal window on browser's back/forward button click.
             */
            $rootScope.$on('$locationChangeStart', function() {
                $modalInstance.close();
            });

            /**
             * Resets Team form
             */
            $scope.resetTeamForm = function () {
                $scope.$broadcast('show-errors-reset');
                if ($scope.teamForm) {
                    $scope.teamForm.$setPristine();
                    $scope.teamForm.$setUntouched();
                }
                $scope.teamNumber = null;
                $scope.teamFormData = null;
            };

            $scope.resetTeamForm();


            function retranslater() {
                teamData = {
                    teamNumber: $scope.teamFormData.teamNumber,
                    teamName: $scope.teamFormData.teamNumber,
                    teamLeaderFullName: $scope.teamFormData.teamNumber,
                    teamLeaderPhone: $scope.teamFormData.teamNumber,
                    teamLeaderEmail: $scope.teamFormData.teamNumber,
                }
            }


            function isDisassemblyTeamAvailable(teamUsername) {
                DisassemblyTeamServiceCalibrator.isDisassemblyTeamAvailable(teamUsername)
                    .then(function (data) {
                        validator('existTeamLogin', data.data);
                    })
            }

            $scope.checkAll = function (caseForValidation) {
                switch (caseForValidation) {
                    case ('teamNumber'):
                        var teamNumber = $scope.teamFormData.teamNumber;
                        if (teamNumber == null) {
                        } else if ($scope.TEAM_USERNAME_REGEX.test(teamNumber)) {
                            isDisassemblyTeamAvailable(teamNumber)
                        } else {
                            validator('loginTeamValid', false);
                        }
                        break;
                }

            };

            function validator(caseForValidation, isValid) {
                switch (caseForValidation) {
                    case 'existTeamLogin' :
                        $scope.teamNumberValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-error' : 'has-success',
                            message: isValid ? undefined : 'Такий логін вже існує'
                        };
                        break;
                    case 'loginTeamValid' :
                        $scope.teamNumberValidation = {
                            isValid: isValid,
                            css: isValid ? 'has-success' : 'has-error',
                            message: isValid ? undefined : 'К-сть символів не повинна бути меншою за 3\n і більшою за 16 '
                        }
                }
            }


            bValidation = function () {
                if ( $scope.teamNumberValidation === undefined) {
                    $scope.incorrectValue = true;
                    return false;
                } else {
                    return true;
                }
            }

            /**
             * Validates team form before saving
             */
            $scope.onTeamFormSubmit = function () {
                $scope.$broadcast('show-errors-check-validity');
                if (!bValidation()) {

                        retranslater();
                        saveDisassemblyTeam();
                        $modal.open({
                            animation: true,
                            templateUrl: '/resources/app/calibrator/views/modals/disassembly-team-adding-success.html',
                            controller: function ($modalInstance) {
                                this.ok = function () {
                                    $modalInstance.close();
                                }
                            },
                            controllerAs: 'successController',
                            size: 'md'
                        });

                } else {
                    $scope.incorrectValue = true;
                }
            };

            /**
             * Saves new team from the form in database.
             * If everything is ok then resets the team
             * form and updates table with teams.
             */
            function saveDisassemblyTeam() {
                DisassemblyTeamServiceCalibrator.saveDisassemblyTeam(
                    $scope.teamFormData).then(
                    function (data) {
                        if (data == 201) {
                            $scope.closeModal();
                            $scope.resetTeamForm();
                            $rootScope.onTableHandling();
                        }
                    })
                    .error(function (data) {
                        $scope.closeModal();
                        $scope.resetTeamForm();
                        $rootScope.onTableHandling();
                        $modal.open({
                            animation: true,
                            templateUrl: '/resources/app/calibrator/views/modals/disassembly-team-adding-denied.html',
                            controller: function ($modalInstance) {
                                this.ok = function () {
                                    $modalInstance.close();
                                }
                            },
                            controllerAs: 'deniedController',
                            size: 'md'
                        });
                    });
            }


            /**
             * Closes the modal window for adding new
             * team.
             */
            $rootScope.closeModal = function () {
                $modalInstance.close();
            };



            //TODO fix regex
            $scope.TEAM_USERNAME_REGEX = /^[a-z0-9_-]{3,16}$/;
            $scope.TEAM_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d{1}[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.TEAM_LEADER_FULL_NAME_REGEX = /^([A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}\u002d{1}[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20}|[A-Z\u0410-\u042f\u0407\u0406\u0404']{1}[a-z\u0430-\u044f\u0456\u0457\u0454']{1,20})$/;
            $scope.PHONE_REGEX = /^[1-9]\d{8}$/;
            $scope.EMAIL_REGEX = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;

        }]);
