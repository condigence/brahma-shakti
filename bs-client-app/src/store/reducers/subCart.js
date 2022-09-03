import {
  ADD_SUBS_PRODUCT_TO_CART,
  REMOVE_FROM_SUB_CART,
  DECREMENT_SUB_QUANTITY,
  INCREMENT_SUB_QUANTITY,
  EDIT_SUB_PRODUCT,
} from "../actions/type";
import { minValueOne } from "../../utils";

const init = {
  subCart: [],
};

export const subCartReducer = (state = init, action) => {
  switch (action.type) {
    case ADD_SUBS_PRODUCT_TO_CART:
      const subProductId = action.product.id;
      const subProductQty = action.qty ? action.qty : 1;
      const subDetail = action.subscription;
      if (
        state.subCart.findIndex((product) => product.id === subProductId) !== -1
      ) {
        const subCart = state.subCart.reduce((cartAcc, product) => {
          if (product.id === subProductId) {
            subDetail.quantity =
              product.subscription.quantity + subDetail.quantity;
            cartAcc.push({
              ...product,
              // qty: product.qty + subProductQty,
              subscription: subDetail,
              sum:
                ((product.price * product.discount) / 100) *
                (product.qty + subProductQty),
            }); // Increment qty
          } else {
            cartAcc.push(product);
          }

          return cartAcc;
        }, []);

        return { ...state, subCart };
      }

      return {
        ...state,
        subCart: [
          ...state.subCart,
          {
            ...action.product,
            selected_color: action.color,
            selected_size: action.size,
            qty: action.qty,
            subscription: subDetail,
            sum:
              ((action.product.price * action.product.discount) / 100) *
              action.qty,
          },
        ],
      };
    case REMOVE_FROM_SUB_CART:
      return {
        subCart: state.subCart.filter((item) => item.id !== action.product_id),
      };
    case INCREMENT_SUB_QUANTITY:
      const inc_productId = action.product_id;
      const new_cart = state.subCart.reduce((cartAcc, product) => {
        if (product.id === inc_productId) {
          cartAcc.push({
            ...product,
            subscription: {
              ...product.subscription,
              quantity: product.subscription.quantity + 1,
            },
          });
        } else {
          cartAcc.push(product);
        }
        return cartAcc;
      }, []);
      return { ...state, subCart: new_cart };

    case DECREMENT_SUB_QUANTITY:
      const decProductId = action.product_id;
      const decCart = state.subCart.reduce((cartAcc, product) => {
        if (product.id === decProductId) {
          cartAcc.push({
            ...product,
            subscription: {
              ...product.subscription,
              quantity: minValueOne(product.subscription.quantity - 1),
            },
            // qty: minValueOne(product.qty - 1),
          });
        } else {
          cartAcc.push(product);
        }
        return cartAcc;
      }, []);

      return { ...state, subCart: decCart };
    case EDIT_SUB_PRODUCT:
      const editSubDetail = action.subscription;
      if (
        state.subCart.findIndex(
          (product) => product.id === action.product.id
        ) !== -1
      ) {
        // console.log("came");
        const subECart = state.subCart.reduce((cartAcc, product) => {
          if (product.id === action.product.id) {
            cartAcc.push({
              ...product,
              subscription: { ...product.subscription, ...editSubDetail },
            });
          } else {
            cartAcc.push(product);
          }

          return cartAcc;
        }, []);
        // console.log(subECart);
        return { ...state, subCart: subECart };
      }
      break
    default:
      return state;
  }
};
