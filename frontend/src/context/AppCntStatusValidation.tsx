import { Context, PropsWithChildren, createContext, useState, useContext } from 'react';

type AppCntStatusValidationType = {
    cntStatus: number;
    setCntStatus: (cntStatus: number) => void;
};

const AppCntStatusValidation: Context<AppCntStatusValidationType> = createContext({
    cntStatus: 0,
    // eslint-disable-next-line
    setCntStatus: (cntStatus) => {},
});
//custom hook
export const useCntStatusValidation = () => {
    return useContext(AppCntStatusValidation);
};

export const AppCntStatusValidationProvider: React.FC<PropsWithChildren> = ({ children }) => {
    const [cntStatus, setCntStatus] = useState<number>(0);
    return (
        <AppCntStatusValidation.Provider value={{ cntStatus, setCntStatus }}>
            {children}
        </AppCntStatusValidation.Provider>
    );
};
