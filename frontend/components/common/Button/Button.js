export default function Button({ context, color, tcolor, border }) {
  return (
    <>
      <div className="btn">{context}</div>

      <style jsx>
        {`
          .btn {
            width: 160px;
            height: 50px;
            padding: 0px;
            line-height: 50px;
            color: ${tcolor};
            font-size: 18px;
            background: ${color};
            border: ${border};
            text-align: center;
          }
        `}
      </style>
    </>
  );
}

Button.defaultProps = {
  tcolor: "000",
  color: "000",
  border: "none",
};
