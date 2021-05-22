import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { UtilConstants } from "plugins/plugin-common/plugin-common-frontend/src/app/util-constants";
import { UtilFuncoes } from "plugins/plugin-common/plugin-common-frontend/src/app/util-funcoes";
import { Ip } from "plugins/plugin-common/plugin-common-frontend/src/app/util-interface/Ip";
import { SignInResponseDto } from "plugins/plugin-common/plugin-common-frontend/src/app/util-interface/SignInResponseDto";
import { UtilLoader } from "plugins/plugin-common/plugin-common-frontend/src/app/util-loader/util-loader";
import { UtilService } from "plugins/plugin-common/plugin-common-frontend/src/app/util-service";
import { ServiceConfig } from "../service/ServiceConfig";
@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  constructor(
    private utilService: UtilService,
    private matSnackBar: MatSnackBar,
    private utilLoader: UtilLoader,
    private utilFuncoes: UtilFuncoes,
    private serviceConfig: ServiceConfig,
    private router: Router,
    private utilConstants: UtilConstants
  ) { }

  private signInForm = new FormGroup({
    numeroDocumentoCPF: new FormControl(
      "",
      Validators.compose([Validators.required])
    ),
    senha: new FormControl("", Validators.compose([Validators.required])),
  });
  ngOnInit() {
    this.utilFuncoes.routerRedirect("/user-profile");
  }
  verificarNumeroCPF() {
    this.signInForm = this.utilFuncoes.validaNumeroDocumentoCPF(
      this.signInForm
    );
  }

  realizarLogin() {
    this.utilLoader
      .show()
      .then((isLoading: HTMLIonLoadingElement | boolean) => {
        this.utilFuncoes.consultarIpDispositivo().then((ip: Ip) => {
          this.signInForm.markAllAsTouched();
          this.signInForm = this.utilFuncoes.validaNumeroDocumentoCPF(
            this.signInForm
          );
          if (this.signInForm.valid) {
            this.serviceConfig.serviceInterface.login.json = {
              numeroDocumentoCPF: this.utilFuncoes.removerMascaraCPF(
                this.signInForm
              ),
              senha: this.signInForm.get("senha").value,
              ipDispositivo: ip.ip,
            };
            this.utilService
              .service(this.serviceConfig.serviceInterface.login)
              .then((signInResponseDto: SignInResponseDto) => {
                this.matSnackBar.open(
                  signInResponseDto.mensagemDto.codigo +
                  " - " +
                  signInResponseDto.mensagemDto.mensagem,
                  "X"
                );
                if (signInResponseDto.mensagemDto.status === true) {
                  localStorage.setItem(
                    this.utilConstants.SESSAO.USUARIO,
                    signInResponseDto.numeroDocumentoCPF
                  );
                  localStorage.setItem(
                    this.utilConstants.SESSAO.TOKEN,
                    signInResponseDto.token
                  );
                  if (this.utilFuncoes.verificarSessaoUsuario()) {
                    this.utilLoader.hide(isLoading);
                    this.utilFuncoes.routerRedirect("/user-profile");
                  } else {
                    this.matSnackBar.open(
                      "Não foi possivel identificar a sessão de usuario, favor entrar em contato com o suporte.",
                      "X"
                    );
                  }
                } else if (signInResponseDto.mensagemDto.codigo ==  11) {
                  this.signInForm.get("senha").setValue("");
                  this.signInForm.get("senha").setErrors({ incorrect: true });
                  this.signInForm
                    .get("numeroDocumentoCPF")
                    .setErrors({ incorrect: true });
                }
                this.utilLoader.hide(isLoading);
              })
              .catch((err) => {
                this.utilLoader.hide(isLoading); this.matSnackBar.open(
                  "Não foi possivel realizar a conexão com os nossos servidores.",
                  "X"
                );
              });
          } else {
            this.matSnackBar.open(
              "Favor corrigir os campos marcados em vermelho.",
              "X"
            );
            this.utilLoader.hide(isLoading);
          }
        });
      });
  }
}
