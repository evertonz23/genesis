angular.module('app')
  .controller('perfilEditarController', function ($scope, $routeParams, authService, $location, toastr,
    loginService, homeService, colaboradorService) {
        $scope.editar = editar;
        $scope.verHome = verHome;
        $scope.verPerfil = verPerfil;
    
    buscarColaborador($routeParams.id);
    var colab = authService.getUsuario();
    if(colab.id != $routeParams.id){
        console.log(colab.id);
        console.log( $routeParams.id);
        toastr.error('Você não pode alterar outro perfil');
        $location.path('/home');
    }

    function buscarColaborador(id) {
        colaboradorService.buscarDadosColaborador(id).then(function (response) {
            $scope.colaborador = response.data;
        })
    }

    function editar(colaborador) {
        colaboradorService.atualizarColaborardor(colaborador).then(function () {
            toastr.success('Perfil atualizado');
            $location.path('/home');
        }, function () {
            toastr.error('Ops... Algo deu errado');
        })
    }

    function verHome() {
        $location.path('/home');
    }

    function verPerfil() {
        $location.path('/perfil');
    }
});