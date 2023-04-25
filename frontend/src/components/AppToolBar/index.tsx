import play from '../../img/play.svg';
import { Dropdown, DropdownButton } from 'react-bootstrap';

//import useSWR from 'swr';
//import { fetchCustomData } from './api';

export const AppToolBar: React.FC = () => {
    /*
    const { data, error, isLoading } = useSWR('/api/stands/', fetchCustomData);
    if (isLoading) {
        return (
            <div className="alert alert-primary" role="alert">
                Data is loading
            </div>
        );
    }
    if (error) {
        return (
            <div className="alert alert-danger" role="alert">
                Cant load data {error.message}
            </div>
        );
    }
    */
    const options = ['Option 1', 'Option 2', 'Option 3'];
    return (
        <div className="row">
            <div className="col-6 d-inline d-flex align-items-center">
                <DropdownButton id="dropdown-basic-button" title="Select an option">
                    {options.map((option) => (
                        <Dropdown.Item key={option} onClick={() => option}>
                            {option}
                        </Dropdown.Item>
                    ))}
                </DropdownButton>
            </div>
            <div className="col-6 d-inline d-flex justify-content-center">
                <button type="button" className="btn btn-primary">
                    <img src={play} />
                </button>
            </div>
        </div>
    );
};
//<div>{JSON.stringify(data)}</div>
