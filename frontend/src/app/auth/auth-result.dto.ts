import {JwtResponseDto} from "./jwt-response.dto";

export interface AuthResultDto {
    jwtResponse?: JwtResponseDto;
    error?: string;
}
