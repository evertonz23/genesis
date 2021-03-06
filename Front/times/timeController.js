angular.module('app')
  .controller('timeController', function ($scope, authService, $location, toastr, $routeParams, timesService,
    timeColaboradorService, solicitacaoTrocaTimeService) {

    $scope.solicitarTroca = solicitarTroca;
    $scope.irParaHome = irParaHome;
    $scope.aceitar = aceitar;
    $scope.deletar = deletar;
    $scope.tornarOwner = tornarOwner;
    $scope.rejeitar = rejeitar,


    $scope.membrosTime = [];
    $scope.ownersTime = [];
    var membrosGeral = [];
    var user = authService.getUsuario();
    var countRepetidos = 0;
    $scope.usuarioAutenticado = typeof user !== 'undefined';

    buscarTime($routeParams.id);

    if($scope.usuarioAutenticado){
      verSeEhOwner($routeParams.id);
    } 

    function buscarTime(id) {
      timesService.buscarTimePorIdComFoto(id).then(function (response) {
        $scope.time = response.data;
        $scope.situacaoTela = $scope.time.time.situacao === 'A' ? 'Ativo' : 'Inativo';

        timeColaboradorService.procurarColaboradorTimeId(id).then(function name(response) {
          var colabs = response.data;

          $scope.membrosTime = [];
          $scope.ownersTime = [];
          if($scope.usuarioAutenticado){
            solicitacoesTroca($routeParams.id);
          }
          

          colabs.forEach(function (colab) {
            if (colab.tipo === "M") {
              $scope.membrosTime.push(colab);
              membrosGeral.push(colab);
            } else if (colab.tipo === "O") {
              $scope.ownersTime.push(colab);
              membrosGeral.push(colab);
            }
          }, this);

          if($scope.usuarioAutenticado){
            membrosGeral.forEach(function (membro) {
            if (membro.idColaborador.id === user.id) {
              countRepetidos++;
            }
          }, this);
          if (countRepetidos > 0) {
            $scope.jaEstaNoTime = true;
          }
          $scope.ownersTime.forEach(function (owner) {
            if (owner.idColaborador.id === user.id) {
              owner.naoEhEleMesmo = false;
            } else owner.naoEhEleMesmo = true;
          }, this);
          } 
        })
      });
    };

    function solicitarTroca(idTime) {
      var solicitacaoTroca = {
        "id": 0,
        "idColaborador": {
          "id": user.id
        },
        "idNovotime": {
          "id": idTime
        }
      }
      solicitacaoTrocaTimeService.criarSolicitacao(solicitacaoTroca).then(function () {
        toastr.success('Solicitação enviada', 'Aguarde resposta');
      })
    };

    function irParaHome() {
      $location.path('/home');
    };

    function solicitacoesTroca(id) {
      $scope.solicitacoes = {},
        solicitacaoTrocaTimeService.buscarSolicitacoes(id).then(function (response) {
          $scope.solicitacoes = response.data;
        });
    };

    var timeColaborador = {};

    function verSeEhOwner(id) {
      timeColaboradorService.colaboradorEhOwner().then(function (response) {
        timeColaborador = response.data;
        if (typeof timeColaborador.idTimecwi === 'undefined') {
          $scope.ehOwnerDoTime = false;
        } else {
          if (timeColaborador.idTimecwi.id == id) {
            $scope.ehOwnerDoTime = true;
            solicitacoesTroca(id);
          }
        }
      });
    };

    function aceitar(solicitacao) {
      solicitacaoTrocaTimeService.aceitarSolicitacao(solicitacao).then(function (response) {
        toastr.success(response.data.mensagem);
         buscarTime($routeParams.id);        
      }, function () {
         toastr.error('Ops... Algo deu errado');
      })
    };

    function rejeitar(solicitacao) {
      solicitacaoTrocaTimeService.deletarSolicitacao(solicitacao).then(function (response) {
        toastr.success('Solicitação rejeitada com sucesso');
        solicitacoesTroca($routeParams.id);
      }, function () {
        toastr.error('Ops... Algo deu errado');
      })
    };


    function deletar(colaborador, motivo) {
      let dados = {}
      dados.idUsuario = colaborador.idColaborador.id;
      dados.mensagem = motivo;
      timesService.deletarColaborador(dados)
        .then(function (response) {
          toastr.success(response.data.mensagem);
          buscarTime($routeParams.id);
        }).catch(function (err) {          
        })
    };

    function tornarOwner(colaborador) {
      timesService.tornarOwner(colaborador.idColaborador).then(function (response) {
        toastr.success(response.data.mensagem);
        buscarTime($routeParams.id);
      }, () => toastr.error("Erro ao realizar a operação")
      )
    };

  });