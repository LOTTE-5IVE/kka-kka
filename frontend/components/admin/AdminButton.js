export function AdminButton({ context, color }) {
  return (
    <>
      <div className="btn">{context}</div>
      <style jsx>{`
        .btn {
          margin: 0 auto;
          width: 40px;
          border-radius: 7px;
          font-size: 13px;
          text-align: center;
          line-height: 21px;
          background-color: ${color};
          color: #fff;
        }
      `}</style>
    </>
  );
}
