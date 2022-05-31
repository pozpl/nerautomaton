import {UserEditDto} from "./user-edit.dto";

export interface UserSaveStatusDto {
    user?: UserEditDto;
    status: boolean;
    error?: string;
}
