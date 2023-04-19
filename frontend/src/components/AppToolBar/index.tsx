import play from '../../img/play.svg' // relative path to image 
export const AppToolBar: React.FC = () => {
    return (<div className="row">
            <div className="col-6 d-inline d-flex align-items-center">
                <input type="text" className="form-control" name="searchStend" placeholder="Enter text here" />
            </div>
            <div className="col-6 d-inline d-flex justify-content-center">
                <button type="button" className="btn btn-primary"><img src={play} /></button>
            </div>
        </div>
    );
};
