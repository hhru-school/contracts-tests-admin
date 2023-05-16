import { Context } from 'react';
import { PropsWithChildren, createContext, useState } from 'react';

type AppContextType = {
    standName: string;
    setStandName: (standName: string) => void;
};

export const AppContext: Context<AppContextType> = createContext({
    standName: '',
    // eslint-disable-next-line
    setStandName: (standName) => {},
});

export const AppContextProvider: React.FC<PropsWithChildren> = ({ children }) => {
    const [standName, setStandName] = useState('');
    return (
        <AppContext.Provider value={{ standName, setStandName }}>{children}</AppContext.Provider>
    );
};
