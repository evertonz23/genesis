angular.module('app')
    .config(function ($routeProvider) {
        $routeProvider
            .when('/solicitar', {
                controller: 'loginController',
                templateUrl: 'login/solicitacao.html'
            })
            .when('/login', {
                controller: 'loginController',
                templateUrl: 'login/login.html'
            })
            .when('/primeiroAcesso', {
                controller: 'primeiroAcessoController',
                templateUrl: 'primeiroAcesso/primeiroAcesso.html'
            })
            .when('/home', {
                controller: 'homeController',
                templateUrl: 'home/home.html'
            })
            .when('/admin', {
                controller: 'adminController',
                templateUrl: 'admin/admin.html'
            })
            .when('/feito/editar/:id', {
                controller: 'feitosEditarController',
                templateUrl: 'feitos/editarFeito.html'
            })
            .when('/feito/criar', {
                controller: 'feitosController',
                templateUrl: 'feitos/criarFeito.html'
            })
            .when('/time/criar', {
                controller: 'criacaoTimeController',
                templateUrl: 'times/criar.html'
            })
            .when('/colaborador/criar',{
                controller : 'colaboradorController',
                templateUrl: 'colaborador/criarColab.html'
            })
            .when('/colaborador/editar/:id',{
                controller : 'colaboradorController',
                templateUrl: 'colaborador/editarColab.html'
            })
            .when('/perfil',{
                controller : 'perfilController',
                templateUrl: 'perfil/perfil.html'
            })
            .when('/time/:id',{
                controller : 'timeController',
                templateUrl: 'times/time.html'
            })
            .otherwise('/login');
    });
    