import { Component, OnInit } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { UtilFuncoes } from "plugins/plugin-common/plugin-common-frontend/src/app/util-funcoes";
import { MensagemDto } from "plugins/plugin-common/plugin-common-frontend/src/app/util-interface/mensagemDto";
import { UtilLoader } from "plugins/plugin-common/plugin-common-frontend/src/app/util-loader/util-loader";
import { UtilService } from "plugins/plugin-common/plugin-common-frontend/src/app/util-service";
import { UtilStorage } from "plugins/plugin-common/plugin-common-frontend/src/app/util-storage";
import { ServiceConfig } from "../service/ServiceConfig";

@Component({
  selector: "app-recuperar-senha",
  templateUrl: "./recuperar-senha.component.html",
  styleUrls: ["./recuperar-senha.component.scss"],
})
export class RecuperarSenhaComponent implements OnInit {
  constructor(
    private utilFuncoes: UtilFuncoes,
    private serviceConfig: ServiceConfig,
    private utilService: UtilService,
    private matSnackBar: MatSnackBar,
    private utilLoader: UtilLoader, 
    private utilStorage: UtilStorage
  ) {
    this.utilStorage.limparPilhasDePaginas();
  }

  private recuperarSenhaForm = new FormGroup({
    numeroDocumentoCPF: new FormControl(
      "",
      Validators.compose([Validators.required])
    ),
    email: new FormControl("", Validators.compose([Validators.required])),
  });
  ngOnInit(): void { }

  verificarNumeroCPF() {
    this.recuperarSenhaForm = this.utilFuncoes.validaNumeroDocumentoCPF(
      this.recuperarSenhaForm
    );
  }

  recuperarSenha() {
    this.utilLoader
      .show()
      .then((isLoading: HTMLIonLoadingElement | boolean) => {
        this.recuperarSenhaForm.markAllAsTouched();
        if (this.recuperarSenhaForm.valid) {
          this.verificarNumeroCPF();
          this.serviceConfig.serviceInterface.recuperarSenhaUsuario.json = {
            email: this.recuperarSenhaForm.get("email").value,
            numeroDocumentoCPF: this.utilFuncoes.removerMascaraCPF(
              this.recuperarSenhaForm
            ),
          };
          this.utilService
            .service(this.serviceConfig.serviceInterface.recuperarSenhaUsuario)
            .then((mensagemDto: MensagemDto) => {
              this.utilFuncoes.routerRedirect("/login");
              this.recuperarSenhaForm.reset();
              this.matSnackBar.open(
                mensagemDto.codigo + " - " + mensagemDto.mensagem,
                "X"
              );

              this.utilLoader.hide(isLoading);
            })
            .catch((err) => {
              console.log(err);
              this.utilLoader.hide(isLoading);
            });
        } else {
          if (!this.recuperarSenhaForm.get("email").valid) {
            this.recuperarSenhaForm.get("email").setErrors({ incorrect: true });
          }
          if (!this.recuperarSenhaForm.get("numeroDocumentoCPF").valid) {
            this.recuperarSenhaForm
              .get("numeroDocumentoCPF")
              .setErrors({ incorrect: true });
          }
          this.matSnackBar.open(
            "Favor corrigir os campos marcados em vermelho.",
            "X"
          );
          this.utilLoader.hide(isLoading);
        }
      });
  }
}
