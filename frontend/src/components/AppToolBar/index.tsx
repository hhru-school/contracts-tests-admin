import play from '../../img/play.svg';
import { Dropdown, DropdownButton } from 'react-bootstrap';
import useSWR from 'swr';
import { fetchCustomData } from './api';
import React, { useState } from 'react';

export const AppToolBar: React.FC = () => {
    const { data, error, isLoading } = useSWR('/api/stands/', fetchCustomData);
    const [selectedOption, setSelectedOption] = useState('select element');
    const handleOptionSelect = (option: string) => {
        setSelectedOption(option);
    };
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
        <div className="row mb-3">
            <div className="col-6 d-inline d-flex align-items-center justify-content-center">
                <DropdownButton
                    id="dropdown-basic-button"
                    title={`Element     : ${selectedOption}`}
                >
                    {data['stands'].map((option) => (
                        <Dropdown.Item
                            key={option['name']}
                            onClick={() => handleOptionSelect(option['name'])}
                        >
                            {option['name']}
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
