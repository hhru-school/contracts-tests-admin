import { Context } from 'react';
import { PropsWithChildren, createContext, useState, useContext } from 'react';

type AppContextType = {
    standName: string;
    setStandName: (standName: string) => void;
};

const AppContext: Context<AppContextType> = createContext({
    standName: '',
    // eslint-disable-next-line
    setStandName: (standName) => {},
});
//custom hook
export const useGlobalContext = () => {
    return useContext(AppContext);
};

export const AppContextProvider: React.FC<PropsWithChildren> = ({ children }) => {
    const [standName, setStandName] = useState('');
    return (
        <AppContext.Provider value={{ standName, setStandName }}>{children}</AppContext.Provider>
    );
};
