import React, { userState, useEffect, useState } from "react";
import { Redirect } from "react-router-dom";
import { changeCustomerType } from "../admin/helper/cardHelper";

const Card = ({ product, setReload = (f) => f, reload = undefined }) => {
  const [redirect, setRedirect] = useState(false);

  const customerEmail = product ? product.email : "emailNotFound";
  const customerFirstName = product ? product.firstName : "firstNameNotFound";
  const customerLastName = product ? product.lastName : "lastNameNotFound";

  let customerTypeStr = "Non Premium";

  const cardCustomerType = product
    ? product.customerType
    : "customerTypeNotFound";
  if (product.cardCustomerType === "PREMIUM") {
    customerTypeStr = "Premium";
  }

  const showAddToCart = (cardCustomerType) => {
    return cardCustomerType === "NON_PREMIUM" ? (
      <button
        onClick={function () {
          convertCustomerType();
          // setReload(!reload);

          // window.location.href = "/admin/dashboard";
        }}
        className="btn btn-outline-success mb-2"
      >
        Make Premium
      </button>
    ) : (
      cardCustomerType === "PREMIUM" && (
        <button
          onClick={function () {
            convertCustomerType();
            // setReload(!reload);
          }}
          className="btn btn-outline-warning mb-2"
        >
          Make Non-Premium
        </button>
      )
    );
  };
  const convertCustomerType = () => {
    changeCustomerType(product, () => setRedirect(true));
    return <Redirect to="/home" />;
  };

  const getARedirect = (redirect) => {
    if (redirect) {
      //setRedirect(false);
      //return <Redirect to="/admin/dashboard" />;
      return <Redirect to="/home" />;
    }
  };
  return (
    // <div className="card text-white bg-dark border border-info ">
    //   <div className="card-header lead">{cardTitle}</div>
    //   <div className="card-body">
    //     <ImageHelper product={product} />
    //     <p className="lead bg-success font-weight-normal text-wrap">
    //       {cardCustomerType}
    //     </p>
    //     <p className="btn btn-success rounded  btn-sm px-4">
    //       {cardCustomerType}
    //     </p>
    //     <div className="row">
    //       <div className="col-12">{showAddToCart(addtoCart)}</div>
    //       <div className="col-12">{showRemoveFromCart(removeFromCart)}</div>
    //     </div>
    //   </div>

    <div>
      {/* {getARedirect(redirect)} */}
      <li className="list-group-item list-group-item-action flex-column align-items-start">
        <div className="d-flex w-100 justify-content-between">
          <h5 className="mb-1">{customerEmail}</h5>
          <small>{cardCustomerType}</small>
        </div>
        <p className="mb-1">
          Customer name : {customerFirstName} {customerLastName}
          <br />
          Customer type : {customerTypeStr}
        </p>

        <div className="mb-2">{showAddToCart(cardCustomerType)}</div>
      </li>
      {/* <li className="list-group-item d-flex justify-content-between align-items-center">
        <span class="align-items-center">{cardCustomerType}</span>

        {showAddToCart(cardCustomerType)}
      </li> */}
    </div>
  );
};

export default Card;
