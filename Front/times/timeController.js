angular.module('app')
  .controller('timeController', function ($scope, authService, $location, toastr, $routeParams, timesService, 
  timeColaboradorService, solicitacaoTrocaTimeService) {
    
    $scope.solicitarTroca = solicitarTroca;
    $scope.irParaHome = irParaHome;
  
    $scope.membrosTime = [];
    $scope.ownersTime = [];
    var membrosGeral = [];
    var user = authService.getUsuario();
     var countRepetidos = 0;

    buscarTime($routeParams.id);
    verSeEhOwner($routeParams.id);
  
    function buscarTime(id) {
       timesService.buscarTimePorIdComFoto(id).then(function (response) {
                $scope.time = response.data;
                $scope.situacaoTela = $scope.time.time.situacao === 'A' ? 'Ativo' : 'Inativo';

                timeColaboradorService.procurarColaboradorTimeId(id).then(function name(response) {
                  var colabs = response.data;
                  colabs.forEach(function (colab) {
                  if (colab.tipo === "M") {
                    $scope.membrosTime.push(colab);
                    membrosGeral.push(colab);
                  } else if (colab.tipo === "O") {
                    $scope.ownersTime.push(colab);
                    membrosGeral.push(colab);
                  }
                }, this);
                membrosGeral.forEach(function(membro) {
                  console.log(membrosGeral);
                  if(membro.idColaborador.id === user.id){
                   countRepetidos++;
                  }
                }, this);
                if(countRepetidos > 0){
                  $scope.jaEstaNoTime = true;
                }
              })
        }); 
    };

    function solicitarTroca(idTime){
        var solicitacaoTroca = {
          "id":0,
          "idColaborador":{ "id": user.id },
          "idNovotime":{ "id": idTime }
        }
        solicitacaoTrocaTimeService.criarSolicitacao(solicitacaoTroca).then(function () {
          toastr.success('Solcicitacão enviada', 'Aguarde resposta');
        })
        console.log(solicitacaoTroca);
    }

    function irParaHome() {
      $location.path('/home');
    }

    function solicitacoesTroca(id) {
      solicitacaoTrocaTimeService.buscarSolicitacoes(id).then(function (response) {
          $scope.solicitacoes = response.data;
      });
    }

    var timeColaborador = {};
    function verSeEhOwner(id) {
      timeColaboradorService.colaboradorEhOwner().then(function (response) {
       timeColaborador = response.data;
        console.log(timeColaborador);
        console.log(timeColaborador.idTimecwi.id);
        console.log(id);
           if (timeColaborador.idTimecwi.id == id) {
          $scope.ehOwnerDoTime = true;
          solicitacoesTroca(id);
          debugger
        }
      });
    }
  });