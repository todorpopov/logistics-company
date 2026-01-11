import { useQuery, UseQueryResult } from "@tanstack/react-query";
import axios from "axios";
import {API_URL} from "../../App";
import type { Office } from "./ManageOffices";

export const useGetOffices = ():
    UseQueryResult<Office[], Error> => useQuery<Office[], Error>({
    queryKey: ["offices"],
    queryFn: async () => {
        const { data } = await axios.get<Office[]>(`${API_URL}/api/office`);
        return data;
    },
    refetchInterval: 5000
});
