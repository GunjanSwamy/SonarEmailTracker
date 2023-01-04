import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { changeCustomerType } from "../admin/helper/cardHelper";
import { isAutheticated } from "../auth/helper/index";
import Base from "../core/Base";
import Card from "../core/Card";
import { getCustomers } from "../core/helper/coreapicalls";

const AdminDashBoard = () => {
  const [customers, setCustomers] = useState([]);
  const [reload, setReload] = useState(false);

  const [error, setError] = useState(false);

  const loadAllCustomers = () => {
    getCustomers().then((data) => {
      //console.log("customer is adb" + data.response);
      setCustomers(data.response);
    });
  };

  useEffect(() => {
    loadAllCustomers();
  }, [reload]);

  const adminLeftSide = () => {
    return (
      <div className="card">
        <h4 className="card-header bg-dark text-white">Admin Navigator</h4>
        <ul className="list-group">
          <li className="list-group-item">
            <Link to="/admin/create/category" className="nav-link text-success">
              Create Categories
            </Link>
          </li>
          <li className="list-group-item">
            <Link to="/admin/create/product" className="nav-link text-success">
              Create Product
            </Link>
          </li>
          <li className="list-group-item">
            <Link to="/admin/products" className="nav-link text-success">
              Manage Products
            </Link>
          </li>
          <li className="list-group-item">
            <Link to="/admin/orders" className="nav-link text-success">
              Manage Orders
            </Link>
          </li>
        </ul>
      </div>
    );
  };

  // return (
  //   <Base title="Home Page" description="Welcome to the Tshirt Store">
  //     <div className="row text-center">
  //       <h1 className="text-white">all of customers</h1>
  //       <div className="row">
  //         {customers.map((name, email) => {
  //           return (
  //             <div key={email} className="col-4 mb-4">
  //               <Card />
  //             </div>
  //           );
  //         })}
  //       </div>
  //     </div>
  //   </Base>
  // );

  const adminRightSide = () => {
    return (
      <div>
        {/* <h1 className="text-white">All of customers</h1>
        <br /> */}
        <ul>
          {customers.map((customerItr, email) => {
            return (
              <div
                className="list-group-item list-group-item-action flex-column align-items-start "
                key={email}
              >
                <Card
                  product={customerItr}
                  setReload={setReload}
                  reload={reload}
                />
              </div>
            );
          })}
        </ul>
      </div>
      /*<div className="card mb-4">
        <h4 className="card-header">Admin information</h4>
        <ul className="list-group">
          <li className="list-group-item">
            <span className="badge badge-success mr-2">Name:</span> LVK
          </li>
          <li className="list-group-item">
            <span className="badge badge-success mr-2">Email:</span>
            lvk@gmail.com
          </li>
          <li className="list-group-item">
            <span className="badge badge-success mr-2">Customer Type:</span>
            PREMIUM
          </li>
          <li className="list-group-item">
            <span className="badge badge-danger mr-2">
              Change Customer Type
            </span>
          </li>
        </ul>
      </div>*/
    );
  };
  return (
    <Base
      title="Welcome to Admin dash baord"
      description="Manage SONAR customers types here"
      className="container bg-success p-4"
    >
      <div>
        {/* <div className="col-3">{adminLeftSide()}</div> */}
        <div>{adminRightSide()}</div>
      </div>
    </Base>
  );
};

export default AdminDashBoard;
