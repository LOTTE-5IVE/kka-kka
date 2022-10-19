export function AdminButton({ context, color, width, height }) {
  return (
    <>
      <div className="btn">{context}</div>
      <style jsx>{`
        .btn {
          margin: 0 auto;
          width: ${width};
          height: ${height};
          border-radius: 7px;
          font-size: 13px;
          text-align: center;
          line-height: 21px;
          color: #fff;
          background-color: ${color};
          cursor: pointer;
        }
      `}</style>
    </>
  );
}

AdminButton.defaultProps = {
  width: "40px",
  height: "21px",
};
