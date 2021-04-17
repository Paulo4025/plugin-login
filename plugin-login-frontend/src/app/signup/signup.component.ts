import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { UtilConstant } from "plugins/plugin-util-common/util-common-frontend/src/app/util-constants";
import { UtilFuncoes } from "plugins/plugin-util-common/util-common-frontend/src/app/util-funcoes";
import { MensagemDto } from "plugins/plugin-util-common/util-common-frontend/src/app/util-interface/MensagemDto";
import { UtilLoader } from "plugins/plugin-util-common/util-common-frontend/src/app/util-loader/util-loader";
import { UtilService } from "plugins/plugin-util-common/util-common-frontend/src/app/util-service";
import { ServiceConfig } from "../service/ServiceConfig";

@Component({
  selector: "app-signup",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.scss"],
})
export class SignupComponent implements OnInit {
  constructor(
    private utilLoader: UtilLoader,
    private utilConstant: UtilConstant,
    private utilFuncoes: UtilFuncoes,
    private matSnackBar: MatSnackBar,
    private utilService: UtilService,
    private serviceConfig: ServiceConfig,
    private router: Router
  ) { }

  private signUpForm = new FormGroup({
    celular: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.pattern(this.utilConstant.expressaoRegular.numeroCelular),
      ])
    ),
    email: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.pattern(this.utilConstant.expressaoRegular.email),
      ])
    ),
    nomeCompleto: new FormControl(
      "",
      Validators.compose([
        Validators.required,
        Validators.pattern(this.utilConstant.expressaoRegular.nomeCompleto),
      ])
    ),
    numeroDocumentoCPF: new FormControl(
      "",
      Validators.compose([Validators.required])
    ),
    checkTermosServicos: new FormControl(
      false,
      Validators.compose([Validators.required])
    ),
  });

  ngOnInit() {
    //this.utilFuncoes.routerRedirect("/user-profile");
  }

  adicionarMascaraCPF() {
    this.signUpForm = this.utilFuncoes.adicionarMascaraCPF(this.signUpForm);
  }

  realizarPreRegistro() {
    this.utilLoader
      .show()
      .then((isLoading: HTMLIonLoadingElement | boolean) => {
        this.signUpForm.markAllAsTouched();
        this.signUpForm = this.utilFuncoes.validaNumeroDocumentoCPF(
          this.signUpForm
        );
        if (this.signUpForm.get("checkTermosServicos").value === true) {
          if (
            this.signUpForm.valid &&
            this.signUpForm.get("checkTermosServicos").value
          ) {
            this.serviceConfig.service.realizarCadastroBasico.json = {
              celular: this.signUpForm.get("celular").value,
              nomeCompleto: this.signUpForm.get("nomeCompleto").value.toLowerCase(),
              numeroDocumentoCPF: this.utilFuncoes.removerMascaraCPF(this.signUpForm),
              email: this.signUpForm.get("email").value.toLowerCase(),
            };
            this.utilService
              .service(this.serviceConfig.service.realizarCadastroBasico)
              .then((mensagemDto: MensagemDto) => {
                this.matSnackBar.open(
                  mensagemDto.codigo + " - " + mensagemDto.mensagem,
                  "X"
                );
                if (mensagemDto.status === true) {
                  this.utilFuncoes.routerRedirect("/user-profile");
                } else {
                  this.gerenciarCoresFormularioCadastro(mensagemDto);
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
        } else {
          this.matSnackBar.open(
            "Para prosseguir deve-se aceitar os termos e condições de uso.",
            "X"
          );
          this.utilLoader.hide(isLoading);
        }
      });
  }

  gerenciarCoresFormularioCadastro(mensagemDto: MensagemDto) {
    switch (mensagemDto.codigo) {
      case 2:
        this.signUpForm
          .get("numeroDocumentoCPF")
          .setErrors({ incorrect: true });
        this.signUpForm
          .get("email")
          .setErrors({ incorrect: true });
        break;
      case 3:
        this.signUpForm
          .get("email")
          .setErrors({ incorrect: true });
        break;
      case 4:
        this.signUpForm
          .get("numeroDocumentoCPF")
          .setErrors({ incorrect: true });
        break;
      case 46:
        this.signUpForm
          .get("celular")
          .setErrors({ incorrect: true });
        break;
      case 47:
        this.signUpForm
          .get("celular")
          .setErrors({ incorrect: true });
        this.signUpForm
          .get("email")
          .setErrors({ incorrect: true });
        break;
      case 48:
        this.signUpForm
          .get("celular")
          .setErrors({ incorrect: true });
        this.signUpForm
          .get("numeroDocumentoCPF")
          .setErrors({ incorrect: true });
        this.signUpForm
          .get("email")
          .setErrors({ incorrect: true });
        break;
    }
  }
}
