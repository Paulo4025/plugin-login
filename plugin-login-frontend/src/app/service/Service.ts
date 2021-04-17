import { Injectable } from "@angular/core";
import { ListaTipoCidadeDto } from "plugins/plugin-common/plugin-common-frontend/src/app/util-interface/ListaTipoCidadeDto";
import { ListaTipoEstadoDto } from "plugins/plugin-common/plugin-common-frontend/src/app/util-interface/ListaTipoEstadoDto";
import { UserResponseDto } from "plugins/plugin-common/plugin-common-frontend/src/app/util-interface/UserResponseDto";

@Injectable()
export class Service {
    constructor() { }

    public userInfo: UserResponseDto = undefined;
    public listaTipoEstadoDto: ListaTipoEstadoDto = undefined;
    public listaTipoCidadeDto: ListaTipoCidadeDto = undefined;


}