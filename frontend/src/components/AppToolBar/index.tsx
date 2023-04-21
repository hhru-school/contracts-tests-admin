import play from '../../img/play.svg';
import useSWR from 'swr';
import { fetchCustomData } from './api';

export const AppToolBar: React.FC = () => {
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

    return (
        <div className="row">
            <div className="col-6 d-inline d-flex align-items-center">
                <input
                    type="text"
                    className="form-control"
                    name="searchStend"
                    placeholder="Enter text here"
                />
            </div>
            <div className="col-6 d-inline d-flex justify-content-center">
                <button type="button" className="btn btn-primary">
                    <img src={play} />
                </button>
            </div>
            <div>{JSON.stringify(data)}</div>
        </div>
    );
};
