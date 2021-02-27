import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { UtilConstant } from "plugins/plugin-util-common/util-common-frontend/src/app/util-constants";
import { UtilFuncoes } from "plugins/plugin-util-common/util-common-frontend/src/app/util-funcoes";
import { SignInResponseDto } from "plugins/plugin-util-common/util-common-frontend/src/app/util-interface/SignInResponseDto";
import { UtilLoader } from "plugins/plugin-util-common/util-common-frontend/src/app/util-loader/util-loader";
import { UtilService } from "plugins/plugin-util-common/util-common-frontend/src/app/util-service";
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
    private utilConstant: UtilConstant
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
        this.signInForm.markAllAsTouched();
        this.signInForm = this.utilFuncoes.validaNumeroDocumentoCPF(
          this.signInForm
        );
        if (this.signInForm.valid) {
          this.serviceConfig.service.login.json = {
            numeroDocumentoCPF: this.utilFuncoes.removerMascaraCPF(
              this.signInForm
            ),
            senha: this.signInForm.get("senha").value,
          };
          this.utilService
            .service(this.serviceConfig.service.login)
            .then((SignInResponseDto: SignInResponseDto) => {
              this.matSnackBar.open(
                SignInResponseDto.mensagemDto.codigo +
                " - " +
                SignInResponseDto.mensagemDto.mensagem,
                "X"
              );
              if (SignInResponseDto.mensagemDto.status === true) {
                localStorage.setItem(
                  this.utilConstant.constants.sessao.usuario,
                  SignInResponseDto.numeroDocumentoCPF
                );
                localStorage.setItem(
                  this.utilConstant.constants.sessao.token,
                  SignInResponseDto.token
                );
                if (this.utilFuncoes.verificarSessaoUsuario()) {
                  this.utilFuncoes.routerRedirect("/user-profile");
                } else {
                  this.matSnackBar.open(
                    "Não foi possivel identificar a sessão de usuario, favor entrar em contato com o suporte.",
                    "X"
                  );
                }
              } else {
                switch (SignInResponseDto.mensagemDto.codigo) {
                  case 5:
                    this.signInForm.get("senha").setValue("");
                    this.signInForm.get("senha").setErrors({ incorrect: true });
                    this.signInForm
                      .get("numeroDocumentoCPF")
                      .setErrors({ incorrect: true });
                    break;
                }
              }
            })
            .catch((err) => {
              this.matSnackBar.open(
                "Não foi possivel realizar a conexão com os nossos servidores.",
                "X"
              );
            })
            .then(() => {
              this.utilLoader.hide(isLoading);
            });
        } else {
          this.matSnackBar.open(
            "Favor corrigir os campos marcados em vermelho.",
            "X"
          );
          this.utilLoader.hide(isLoading);
        }
      });
  }
}
