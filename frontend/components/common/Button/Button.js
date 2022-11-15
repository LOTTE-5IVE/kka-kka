export default function Button({ context, color, tcolor, border, unvalid }) {
  return (
    <>
      <button className="btn" disabled={unvalid}>
        {context}
      </button>

      <style jsx>
        {`
          .btn:disabled {
            background: #dadada;
            color: white;
            border: none;
          }
          .btn {
            width: 160px;
            height: 50px;
            padding: 0px;
            color: ${tcolor};
            font-size: 18px;
            background: ${color};
            border: ${border};
            text-align: center;
            cursor: pointer;
            border-radius: 1em;
          }
        `}
      </style>
    </>
  );
}

Button.defaultProps = {
  tcolor: "000",
  color: "rgb(242, 29, 47)",
  border: "none",
  unvalid: true,
};
